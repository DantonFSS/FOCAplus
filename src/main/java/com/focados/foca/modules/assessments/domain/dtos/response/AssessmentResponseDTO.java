package com.focados.foca.modules.assessments.domain.dtos.response;

import lombok.Data;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class AssessmentResponseDTO {
    private UUID id;
    private String title;
    private String description;
    private Integer pointsPossible;
    private ZonedDateTime date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer grade;
    private UUID disciplineInstanceId;
}