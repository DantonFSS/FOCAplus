package com.focados.foca.modules.periods.database.repository;

import com.focados.foca.modules.periods.database.entity.PeriodInstanceModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PeriodInstanceRepository extends JpaRepository<PeriodInstanceModel, UUID> {
    List<PeriodInstanceModel> findByUserCourseIdOrderByPositionAsc(UUID userCourseId);
    Optional<PeriodInstanceModel> findByUserCourseIdAndPeriodTemplateId(UUID userCourseId, UUID periodTemplateId);
}