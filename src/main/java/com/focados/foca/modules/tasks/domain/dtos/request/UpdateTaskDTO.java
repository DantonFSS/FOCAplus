package com.focados.foca.modules.tasks.domain.dtos.request;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record UpdateTaskDTO(
        String title,
        String description,
        Integer pointsPossible,
        ZonedDateTime dueDate,
        Boolean completed
) {}