package com.focados.foca.modules.courses.domain.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Data
public class CreateCourseDto {
    @NotNull
    @NotBlank(message = "Name must not be empty or null")
    private String name;

    @NotNull
    @NotBlank(message = "Level must not be empty or null")
    private String level;

    @NotNull
    @NotBlank(message = "Division type must not be empty or null")
    private String divisionType;

    private String institutionName;

    private LocalDate startDate;

    private LocalDate expectedEndDate;

    private String period;
}

