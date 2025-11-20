package com.focados.foca.modules.tasks.domain.dtos.response;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

public record TaskResponseDTO(
        UUID id,
        UUID disciplineInstanceId,
        String title,
        String description,
        Integer pointsPossible,
        ZonedDateTime dueDate,
        ZonedDateTime createdAt,
        Boolean completed,
        ZonedDateTime completedAt
) {}