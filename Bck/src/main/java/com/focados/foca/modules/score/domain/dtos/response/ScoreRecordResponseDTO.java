package com.focados.foca.modules.score.domain.dtos.response;

import java.util.UUID;
import java.time.ZonedDateTime;

public record ScoreRecordResponseDTO(
        UUID id,
        UUID userId,
        UUID userCourseId,
        UUID disciplineInstanceId,
        String sourceType,
        UUID sourceId,
        Integer points,
        ZonedDateTime createdAt
) {}