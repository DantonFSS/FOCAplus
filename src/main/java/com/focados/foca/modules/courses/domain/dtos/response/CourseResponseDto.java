package com.focados.foca.modules.courses.domain.dtos.response;

import com.focados.foca.modules.courses.database.entity.enums.CourseLevel;
import com.focados.foca.modules.courses.database.entity.enums.CourseStatus;
import com.focados.foca.modules.courses.database.entity.enums.DivisionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDto {
    private UUID id;
    private String name;
    private CourseLevel level;
    private DivisionType divisionType;
    private int divisionsCount;
    private String institutionName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String address;
    private boolean online;
    private CourseStatus status;
    private List<String> phones;
    private List<String> emails;
    private String shareCode;
    private UUID createdBy;
}

