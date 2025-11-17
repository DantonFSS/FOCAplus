package com.focados.foca.modules.studySessions.database.repository;

import com.focados.foca.modules.studySessions.database.entity.StudySessionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

/**
 * Repositório do módulo StudySessions
 */
public interface StudySessionRepository extends JpaRepository<StudySessionModel, UUID> {
    List<StudySessionModel> findAllByUserId(UUID userId);
    List<StudySessionModel> findAllByUserCourseId(UUID userCourseId);
    List<StudySessionModel> findAllByDisciplineInstanceId(UUID disciplineInstanceId);
}