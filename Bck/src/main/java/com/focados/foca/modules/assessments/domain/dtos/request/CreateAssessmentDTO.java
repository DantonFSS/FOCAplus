package com.focados.foca.modules.assessments.domain.dtos.request;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class CreateAssessmentDTO {
    private UUID disciplineInstanceId;
    private String title;
    private String description;
    private Integer pointsPossible;
    private ZonedDateTime date;
    private LocalTime startTime;
    private LocalTime endTime;
}