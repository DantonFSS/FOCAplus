package com.focados.foca.modules.assessments.domain.services;

import com.focados.foca.modules.assessments.database.entity.AssessmentCollaboratorModel;
import com.focados.foca.modules.assessments.database.repository.AssessmentCollaboratorRepository;
import com.focados.foca.modules.assessments.database.repository.AssessmentRepository;
import com.focados.foca.modules.assessments.domain.dtos.response.AssessmentCollaboratorResponseDTO;
import com.focados.foca.modules.courses.database.entity.enums.UserCourseRole;
import com.focados.foca.modules.users.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssessmentCollaboratorService {
    private final AssessmentCollaboratorRepository collabRepository;
    private final UserRepository userRepository;
    private final AssessmentRepository assessmentRepository;

    public List<AssessmentCollaboratorResponseDTO> getCollaboratorsOfAssessment(UUID assessmentId) {
        return collabRepository.findAllByAssessmentId(assessmentId).stream()
                .map(collab -> {
                    var dto = new AssessmentCollaboratorResponseDTO();
                    dto.setUserId(collab.getUser().getId());
                    dto.setName(collab.getUser().getName());
                    dto.setUsername(collab.getUser().getUsername());
                    dto.setRole(collab.getRole());
                    return dto;
                })
                .toList();
    }

    public void inviteUserToAssessment(UUID assessmentId, String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        var assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new IllegalArgumentException("Assessment não encontrada"));

        boolean alreadyCollaborator = collabRepository
                .findByAssessmentIdAndUserId(assessmentId, user.getId()).isPresent();
        if (alreadyCollaborator) return;

        var collab = new AssessmentCollaboratorModel();
        collab.setAssessment(assessment);
        collab.setUser(user);
        collab.setRole(UserCourseRole.MEMBER);
        collabRepository.save(collab);
    }

    @Transactional
    public void removeUserFromAssessment(UUID assessmentId, UUID userId) {
        var collab = collabRepository.findByAssessmentIdAndUserId(assessmentId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Colaborador não encontrado para esta assessment"));
        collabRepository.delete(collab);
    }
}