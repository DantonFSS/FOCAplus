package com.focados.foca.modules.courses.database.repository;

import com.focados.foca.modules.courses.database.entity.UserCourseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourseModel, UUID> {
    List<UserCourseModel> findByUserId(UUID userId);
    boolean existsByUserIdAndCourseTemplateId(UUID userId, UUID courseTemplateId);
    Optional<UserCourseModel> findByUserIdAndCourseTemplateId(UUID userId, UUID courseTemplateId);
}
