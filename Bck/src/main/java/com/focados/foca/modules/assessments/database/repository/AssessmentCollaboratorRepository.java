package com.focados.foca.modules.assessments.database.repository;

import com.focados.foca.modules.assessments.database.entity.AssessmentCollaboratorModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface AssessmentCollaboratorRepository extends JpaRepository<AssessmentCollaboratorModel, UUID> {
    List<AssessmentCollaboratorModel> findAllByAssessmentId(UUID assessmentId);
    Optional<AssessmentCollaboratorModel> findByAssessmentIdAndUserId(UUID assessmentId, UUID userId);
    void deleteByAssessmentIdAndUserId(UUID assessmentId, UUID userId);
}