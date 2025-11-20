package com.focados.foca.modules.assessments.domain.dtos.request;

import lombok.Data;
import java.time.LocalTime;
import java.time.ZonedDateTime;

@Data
public class UpdateAssessmentDTO {
    private String title;
    private String description;
    private Integer pointsPossible;
    private ZonedDateTime date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String grade;
}