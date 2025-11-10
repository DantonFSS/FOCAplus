package com.focados.foca.modules.periods.database.repository;

import com.focados.foca.modules.periods.database.entity.PeriodTemplateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PeriodTemplateRepository extends JpaRepository<PeriodTemplateModel, UUID> {
    List<PeriodTemplateModel> findByCourseTemplateIdOrderByPositionAsc(UUID courseTemplateId);
}