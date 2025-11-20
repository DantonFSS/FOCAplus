package com.focados.foca.modules.score.domain.services;

import com.focados.foca.modules.materias.database.repository.DisciplineInstanceRepository;
import com.focados.foca.modules.courses.database.repository.UserCourseRepository;
import com.focados.foca.modules.score.database.entity.ScoreRecordModel;
import com.focados.foca.modules.score.database.entity.enums.ScoreSourceType;
import com.focados.foca.modules.score.database.repository.ScoreRecordRepository;
import com.focados.foca.modules.score.domain.dtos.mappers.ScoreRecordMapper;
import com.focados.foca.modules.score.domain.dtos.response.ScoreRecordResponseDTO;
import com.focados.foca.modules.tasks.database.entity.TaskModel;
import com.focados.foca.modules.users.database.repository.UserRepository;
import com.focados.foca.modules.users.domain.services.AuthUtil;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScoreRecordService {

    private final ScoreRecordRepository scoreRecordRepository;
    private final UserRepository userRepository;
    private final UserCourseRepository userCourseRepository;
    private final DisciplineInstanceRepository disciplineInstanceRepository;
    private final ScoreRecordMapper scoreRecordMapper;

    public ScoreRecordModel onTaskCompleted(TaskModel task, UUID userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        var disciplineInstance = disciplineInstanceRepository.findById(task.getDisciplineInstance().getId())
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));
        var userCourse = disciplineInstance.getUserCourse();

        var record = new ScoreRecordModel();
        record.setUser(user);
        record.setUserCourse(userCourse);
        record.setDisciplineInstance(disciplineInstance);
        record.setSourceType(ScoreSourceType.TASK);
        record.setSourceId(task.getId());
        record.setPoints(task.getPointsPossible());
        scoreRecordRepository.save(record);

        return record;
    }

    @Transactional
    public void onTaskUncompleted(TaskModel task) {
        scoreRecordRepository.deleteBySourceIdAndSourceType(task.getId(), ScoreSourceType.TASK);
    }

    public List<ScoreRecordResponseDTO> findByUserId() {
        UUID userId = AuthUtil.getAuthenticatedUserId();
        return scoreRecordRepository.findByUserId(userId)
                .stream().map(scoreRecordMapper::toDTO).toList();
    }
    public List<ScoreRecordResponseDTO> findByDisciplineInstanceId(UUID disciplineInstanceId) {
        return scoreRecordRepository.findByDisciplineInstanceId(disciplineInstanceId)
                .stream().map(scoreRecordMapper::toDTO).toList();
    }
    public List<ScoreRecordResponseDTO> findByUserCourseId(UUID userCourseId) {
        return scoreRecordRepository.findByUserCourseId(userCourseId)
                .stream().map(scoreRecordMapper::toDTO).toList();
    }
}