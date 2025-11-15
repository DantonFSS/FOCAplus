package com.focados.foca.modules.materias.domain.dtos.request;

import java.time.LocalTime;
import java.util.UUID;

public record CreateDisciplineScheduleDTO(
        UUID disciplineInstanceId,
        int weekday,
        LocalTime startTime,
        LocalTime endTime
) {}