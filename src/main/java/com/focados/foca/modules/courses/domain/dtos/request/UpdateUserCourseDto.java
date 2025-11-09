package com.focados.foca.modules.courses.domain.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class UpdateUserCourseDto {
    
    // Campos do vínculo que podem ser editados
    private LocalDate customStart;
    private LocalDate customEnd;

    // Campos do template que podem ser editados
    private String name;
    private String level;
    private String divisionType;
    private Integer divisionsCount;
    private String institutionName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String address;
    private Boolean online;
    private String status;
    private List<String> phones;
    private List<String> emails;
}