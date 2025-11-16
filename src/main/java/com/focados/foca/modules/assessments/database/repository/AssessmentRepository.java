package com.focados.foca.modules.assessments.database.repository;

import com.focados.foca.modules.assessments.database.entity.AssessmentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AssessmentRepository extends JpaRepository<AssessmentModel, UUID> {
    List<AssessmentModel> findByDisciplineInstanceId(UUID disciplineInstanceId);
}