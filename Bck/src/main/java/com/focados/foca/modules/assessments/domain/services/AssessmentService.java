package com.focados.foca.modules.assessments.domain.services;

import com.focados.foca.modules.assessments.database.entity.AssessmentCollaboratorModel;
import com.focados.foca.modules.assessments.database.entity.AssessmentModel;
import com.focados.foca.modules.assessments.database.repository.AssessmentCollaboratorRepository;
import com.focados.foca.modules.assessments.database.repository.AssessmentRepository;
import com.focados.foca.modules.assessments.domain.dtos.mappers.AssessmentMapper;
import com.focados.foca.modules.assessments.domain.dtos.request.CreateAssessmentDTO;
import com.focados.foca.modules.assessments.domain.dtos.request.UpdateAssessmentDTO;
import com.focados.foca.modules.assessments.domain.dtos.response.AssessmentResponseDTO;
import com.focados.foca.modules.materias.database.entity.enums.GradeSystem;
import com.focados.foca.modules.materias.database.repository.DisciplineInstanceRepository;
import com.focados.foca.modules.users.database.entity.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssessmentService {
    private final AssessmentRepository assessmentRepository;
    private final AssessmentCollaboratorRepository collabRepository;
    private final DisciplineInstanceRepository disciplineInstanceRepository;

    // Map para transformar nota conceitual em ints
    private static final Map<String, Integer> LETTER_GRADES =
            Map.of("A", 5, "B", 4, "C", 3, "D", 2, "E", 1, "F", 0);

    public AssessmentResponseDTO createAssessment(CreateAssessmentDTO dto) {
        var discipline = disciplineInstanceRepository.findById(dto.getDisciplineInstanceId())
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));

        var assessment = new AssessmentModel();
        assessment.setDisciplineInstance(discipline);
        assessment.setTitle(dto.getTitle());
        assessment.setDescription(dto.getDescription());
        assessment.setPointsPossible(dto.getPointsPossible());
        assessment.setDate(dto.getDate());
        assessment.setStartTime(dto.getStartTime());
        assessment.setEndTime(dto.getEndTime());
        assessment.setGrade(null);

        assessment = assessmentRepository.save(assessment);

        var owner = discipline.getUserCourse().getUser();

        var collab = new AssessmentCollaboratorModel();
        collab.setAssessment(assessment);
        collab.setUser(owner);
        collab.setRole(com.focados.foca.modules.courses.database.entity.enums.UserCourseRole.OWNER);
        collabRepository.save(collab);

        return AssessmentMapper.toResponse(assessment);
    }

    public List<AssessmentResponseDTO> getAllAssessments() {
        return assessmentRepository.findAll().stream()
                .map(AssessmentMapper::toResponse).toList();
    }

    // GET by discipline instance
    public List<AssessmentResponseDTO> getByDisciplineInstance(UUID disciplineInstanceId) {
        return assessmentRepository.findByDisciplineInstanceId(disciplineInstanceId).stream()
                .map(AssessmentMapper::toResponse).toList();
    }

    // GET one
    public AssessmentResponseDTO getOne(UUID id) {
        return assessmentRepository.findById(id)
                .map(AssessmentMapper::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Assessment não encontrada"));
    }

    // PUT (edit)
    @Transactional
    public AssessmentResponseDTO updateAssessment(UUID id, UpdateAssessmentDTO dto) {
        var assessment = assessmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assessment não encontrada."));
        if(dto.getTitle() != null) assessment.setTitle(dto.getTitle());
        if(dto.getDescription() != null) assessment.setDescription(dto.getDescription());
        if(dto.getPointsPossible() != null) assessment.setPointsPossible(dto.getPointsPossible());
        if(dto.getDate() != null) assessment.setDate(dto.getDate());
        if(dto.getStartTime() != null) assessment.setStartTime(dto.getStartTime());
        if(dto.getEndTime() != null) assessment.setEndTime(dto.getEndTime());

        if (dto.getGrade() != null) {
            var discipline = assessment.getDisciplineInstance();
            var gradeSystem = discipline.getGradeSystem();
            Integer gradeValue;

            if (gradeSystem == GradeSystem.LETTER) {
                // Aceita apenas letras de A a F
                Map<String, Integer> LETTER_GRADES = Map.of("A", 5, "B", 4, "C", 3, "D", 2, "E", 1, "F", 0);
                gradeValue = LETTER_GRADES.getOrDefault(dto.getGrade().toUpperCase(), null);
                if (gradeValue == null)
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nota conceitual inválida (A-F)");
            } else {
                // Aceita apenas inteiros
                try {
                    gradeValue = Integer.valueOf(dto.getGrade());
                } catch (NumberFormatException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nota deve ser número inteiro para o sistema " + gradeSystem);
                }
                int max = switch (gradeSystem) {
                    case NUMERIC_5 -> 5;
                    case NUMERIC_10 -> 10;
                    case NUMERIC_100 -> 100;
                    case NUMERIC_1000 -> 1000;
                    default -> 10;
                };
                if (gradeValue < 0 || gradeValue > max)
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nota fora do permitido pelo sistema (" + gradeSystem + ")");
            }
            assessment.setGrade(gradeValue);
        }

        assessment = assessmentRepository.save(assessment);
        return AssessmentMapper.toResponse(assessment);
    }

    // DELETE
    @Transactional
    public void deleteAssessment(UUID id) {
        if (!assessmentRepository.existsById(id)) {
            throw new IllegalArgumentException("Assessment não encontrada.");
        }
        assessmentRepository.deleteById(id);
    }
}