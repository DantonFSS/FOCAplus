package com.focados.foca.modules.periods.domain.dtos.response;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;

@Data
public class PeriodTemplateResponseDto {
    private UUID id;
    private UUID courseTemplateId;
    private String name;
    private Integer position;
    private LocalDate plannedStart;
    private LocalDate plannedEnd;
}