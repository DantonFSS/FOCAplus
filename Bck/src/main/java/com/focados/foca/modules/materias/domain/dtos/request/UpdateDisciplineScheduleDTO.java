package com.focados.foca.modules.materias.domain.dtos.request;

import java.time.LocalTime;

public record UpdateDisciplineScheduleDTO(
        int weekday,
        LocalTime startTime,
        LocalTime endTime
) {}