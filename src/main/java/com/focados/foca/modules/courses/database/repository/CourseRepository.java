package com.focados.foca.modules.courses.database.repository;

import com.focados.foca.modules.courses.database.entity.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositório do módulo Courses
 */
@Repository
public interface CourseRepository extends JpaRepository<CourseModel, UUID> {
    List<CourseModel> findByCreatedById(UUID userId);
    Optional<CourseModel> findByShareCode(String shareCode);

}

