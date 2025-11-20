package com.focados.foca.modules.periods.domain.dtos.request;

import lombok.Data;

import java.util.UUID;

@Data
public class CreatePeriodInstanceDto {
    private UUID userCourseId;
}