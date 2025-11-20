package com.focados.foca.modules.materias.domain.dtos.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class DisciplineInstanceResponseDto {
    private UUID id;
    private UUID userCourseId;
    private UUID disciplineTemplateId;
    private UUID periodInstanceId;
    private LocalDate plannedStart;
    private LocalDate plannedEnd;
    private String status;
    private BigDecimal grade;
    private String gradeSystem;
    private Integer assessmentsCount;
    private ZonedDateTime createdAt;
    private String name;
    private String notes;
}