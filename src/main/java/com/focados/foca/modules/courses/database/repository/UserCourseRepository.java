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
    Optional<UserCourseModel> findByShareCode(String shareCode);
    boolean existsByUserIdAndCourseTemplateId(UUID userId, UUID courseTemplateId);
    boolean existsByShareCode(String shareCode);
}
