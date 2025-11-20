package com.focados.foca.modules.courses.domain.dtos.response;

import com.focados.foca.modules.courses.database.entity.enums.CourseLevel;
import com.focados.foca.modules.courses.database.entity.enums.CourseStatus;
import com.focados.foca.modules.courses.database.entity.enums.DivisionType;
import com.focados.foca.modules.courses.database.entity.enums.UserCourseRole;
import lombok.Data;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class UserCourseResponseDto {
    // Campos do UserCourseModel
    private UUID userCourseId;
    private UserCourseRole role;
    private boolean accepted;
    private ZonedDateTime joinedAt;
    private LocalDate customStart;
    private LocalDate customEnd;

    // Campos do CourseModel
    private UUID templateId;
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