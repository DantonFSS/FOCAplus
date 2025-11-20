package com.focados.foca.modules.periods.domain.dtos.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class PeriodInstanceResponseDto {
    private UUID id;
    private UUID userCourseId;
    private UUID periodTemplateId;
    private String name;
    private Integer position;
    private LocalDate plannedStart;
    private LocalDate plannedEnd;
}