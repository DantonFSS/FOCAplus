package com.focados.foca.modules.studySessions.domain.services;

import com.focados.foca.modules.courses.database.repository.UserCourseRepository;
import com.focados.foca.modules.materias.database.repository.DisciplineInstanceRepository;
import com.focados.foca.modules.studySessions.database.entity.StudySessionModel;
import com.focados.foca.modules.studySessions.database.repository.StudySessionRepository;
import com.focados.foca.modules.studySessions.domain.dtos.mappers.StudySessionMapper;
import com.focados.foca.modules.studySessions.domain.dtos.request.CreateStudySessionDTO;
import com.focados.foca.modules.studySessions.domain.dtos.response.StudySessionResponseDTO;
import com.focados.foca.modules.users.database.repository.UserRepository;
import com.focados.foca.modules.users.domain.services.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudySessionService {
    private final StudySessionRepository repository;
    private final UserCourseRepository userCourseRepo;
    private final DisciplineInstanceRepository disciplineInstanceRepo;
    private final UserRepository userRepo;

    public StudySessionResponseDTO createSession(CreateStudySessionDTO dto) {
        UUID userId = AuthUtil.getAuthenticatedUserId();
        var user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        var userCourse = userCourseRepo.findById(dto.getUserCourseId())
                .orElseThrow(() -> new IllegalArgumentException("UserCourse não encontrado"));

        var session = new StudySessionModel();
        session.setUser(user);
        session.setUserCourse(userCourse);
        session.setSessionType(dto.getSessionType());
        session.setMode(dto.getMode());
        session.setDurationSeconds(dto.getDurationSeconds());
        session.setPomodoroCycles(dto.getPomodoroCycles());
        session.setPointsEarned(dto.getPointsEarned());
        session.setStartedAt(dto.getStartedAt());
        session.setEndedAt(dto.getEndedAt());

        if (dto.getDisciplineInstanceId() != null) {
            session.setDisciplineInstance(disciplineInstanceRepo.findById(dto.getDisciplineInstanceId())
                    .orElse(null));
        }

        session = repository.save(session);
        return StudySessionMapper.toResponse(session);
    }

    public List<StudySessionResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(StudySessionMapper::toResponse)
                .toList();
    }

    public List<StudySessionResponseDTO> getAllByDiscipline(UUID disciplineInstanceId) {
        return repository.findAllByDisciplineInstanceId(disciplineInstanceId)
                .stream()
                .map(StudySessionMapper::toResponse)
                .toList();
    }

    public StudySessionResponseDTO getOne(UUID id) {
        var session = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sessão de estudo não encontrada"));
        return StudySessionMapper.toResponse(session);
    }

    public void deleteSession(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sessão não encontrada");
        }
        repository.deleteById(id);
    }
}