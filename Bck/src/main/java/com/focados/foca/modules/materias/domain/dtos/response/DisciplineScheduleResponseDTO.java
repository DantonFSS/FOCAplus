package com.focados.foca.modules.materias.domain.dtos.response;

import java.time.LocalTime;
import java.util.UUID;

public record DisciplineScheduleResponseDTO(
        UUID id,
        UUID disciplineInstanceId,
        int weekday,
        LocalTime startTime,
        LocalTime endTime
) {}