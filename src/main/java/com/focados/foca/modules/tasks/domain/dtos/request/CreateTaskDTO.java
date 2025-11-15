package com.focados.foca.modules.tasks.domain.dtos.request;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

public record CreateTaskDTO(
        UUID disciplineInstanceId,
        String title,
        String description,
        Integer pointsPossible,
        ZonedDateTime dueDate
) {}