package com.focados.foca.modules.materias.database.repository;

import com.focados.foca.modules.materias.database.entity.DisciplineTemplateModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DisciplineTemplateRepository extends JpaRepository<DisciplineTemplateModel, UUID> {
    List<DisciplineTemplateModel> findByPeriodTemplateId(UUID periodTemplateId);
}