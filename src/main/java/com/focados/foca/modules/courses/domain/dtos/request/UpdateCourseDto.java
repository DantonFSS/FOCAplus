package com.focados.foca.modules.courses.domain.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
@Data
public class UpdateCourseDto {
    @NotNull
    private UUID id;

    @NotNull
    @NotBlank(message = "Name must not be empty or null")
    private String name;

    @NotNull
    @NotBlank(message = "Level must not be empty or null")
    private String level;

    @NotNull
    @NotBlank(message = "Division type must not be empty or null")
    private String divisionType;

    @NotNull(message = "DivisionsCount is required")
    private Integer divisionsCount;

    private String institutionName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String address;
    private Boolean online = false;
    private String status;
    private List<String> phones;
    private List<String> emails;
}