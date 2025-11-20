package com.focados.foca.modules.score.database.repository;

import com.focados.foca.modules.score.database.entity.ScoreRecordModel;
import com.focados.foca.modules.score.database.entity.enums.ScoreSourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ScoreRecordRepository extends JpaRepository<ScoreRecordModel, UUID> {
    Optional<ScoreRecordModel> findBySourceIdAndSourceType(UUID sourceId, ScoreSourceType sourceType);
    @Modifying
    @Transactional
    @Query("delete from ScoreRecordModel s where s.sourceId = :sourceId and s.sourceType = :sourceType")
    void deleteBySourceIdAndSourceType(UUID sourceId, ScoreSourceType sourceType);
    List<ScoreRecordModel> findByUserId(UUID userId);
    List<ScoreRecordModel> findByDisciplineInstanceId(UUID disciplineInstanceId);
    List<ScoreRecordModel> findByUserCourseId(UUID userCourseId);
}