package com.focados.foca.modules.courses.domain.dtos.request;

import com.focados.foca.modules.courses.database.entity.enums.CourseLevel;
import com.focados.foca.modules.courses.database.entity.enums.CourseStatus;
import com.focados.foca.modules.courses.database.entity.enums.DivisionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Data
public class CreateCourseDto {
    @NotNull
    @NotBlank(message = "Name must not be empty or null")
    private String name;

    @NotNull
    @NotBlank(message = "Level must not be empty or null")
    private CourseLevel level;

    @NotNull
    @NotBlank(message = "Division type must not be empty or null")
    private DivisionType divisionType = DivisionType.PERIOD;

    @NotNull(message = "DivisionsCount is required")
    private Integer divisionsCount = 1;

    private String institutionName;

    private LocalDate startDate;

    private LocalDate endDate;

    private String address;
    private boolean online = false;
    private CourseStatus status = CourseStatus.NOT_STARTED;
    private List<String> phones;
    private List<String> emails;
}

