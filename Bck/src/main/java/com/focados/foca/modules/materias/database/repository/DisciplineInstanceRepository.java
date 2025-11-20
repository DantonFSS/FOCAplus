package com.focados.foca.modules.materias.database.repository;

import com.focados.foca.modules.materias.database.entity.DisciplineInstanceModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DisciplineInstanceRepository extends JpaRepository<DisciplineInstanceModel, UUID> {
    List<DisciplineInstanceModel> findByUserCourseId(UUID userCourseId);
    List<DisciplineInstanceModel> findByPeriodInstanceId(UUID periodInstanceId);
}