package com.focados.foca.modules.materias.database.repository;

import com.focados.foca.modules.materias.database.entity.DisciplineScheduleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DisciplineScheduleRepository extends JpaRepository<DisciplineScheduleModel, UUID> {
    List<DisciplineScheduleModel> findAllByDisciplineInstanceId(UUID disciplineInstanceId);
    Optional<DisciplineScheduleModel> findById(UUID id);
}