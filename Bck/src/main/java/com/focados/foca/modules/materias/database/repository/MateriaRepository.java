package com.focados.foca.modules.materias.database.repository;

import com.focados.foca.modules.materias.database.entity.MateriaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repositório do módulo Materias (Subjects)
 */
@Repository
public interface MateriaRepository extends JpaRepository<MateriaModel, UUID> {
    List<MateriaModel> findByCourseId(UUID courseId);
}

