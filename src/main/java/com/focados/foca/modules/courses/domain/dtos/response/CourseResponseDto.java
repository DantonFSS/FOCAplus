package com.focados.foca.modules.courses.domain.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDto {
    private UUID id;
    private String name;
    private String level;
    private String divisionType;
    private String institutionName;
    private LocalDate startDate;
    private LocalDate expectedEndDate;
    private String period;
}

