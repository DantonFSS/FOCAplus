package com.focados.foca.modules.periods.domain.dtos.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UpdatePeriodTemplateDto {
    private String name;
    private LocalDate plannedStart;
    private LocalDate plannedEnd;
}