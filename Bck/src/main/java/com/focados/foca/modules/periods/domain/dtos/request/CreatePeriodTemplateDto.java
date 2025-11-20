package com.focados.foca.modules.periods.domain.dtos.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CreatePeriodTemplateDto {
    private UUID courseTemplateId;
}