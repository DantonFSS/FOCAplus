package com.focados.foca.modules.materias.database.repository;

import com.focados.foca.modules.materias.database.entity.DisciplineTeacherModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DisciplineTeacherRepository extends JpaRepository<DisciplineTeacherModel, UUID> {
    List<DisciplineTeacherModel> findAllByDisciplineInstanceId(UUID disciplineInstanceId);
    Optional<DisciplineTeacherModel> findById(UUID id);
}