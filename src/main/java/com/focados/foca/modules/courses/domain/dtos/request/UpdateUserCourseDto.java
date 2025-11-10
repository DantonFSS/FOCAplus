package com.focados.foca.modules.courses.domain.dtos.request;

import com.focados.foca.modules.courses.database.entity.enums.CourseLevel;
import com.focados.foca.modules.courses.database.entity.enums.CourseStatus;
import com.focados.foca.modules.courses.database.entity.enums.DivisionType;
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
    private CourseLevel level;
    private DivisionType divisionType;
    private Integer divisionsCount;
    private String institutionName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String address;
    private Boolean online;
    private CourseStatus status;
    private List<String> phones;
    private List<String> emails;


}