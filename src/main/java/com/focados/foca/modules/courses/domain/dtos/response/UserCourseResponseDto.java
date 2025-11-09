package com.focados.foca.modules.courses.domain.dtos.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class UserCourseResponseDto {
    // Campos do UserCourseModel
    private UUID userCourseId;
    private String role;
    private String shareCode;
    private boolean accepted;
    private ZonedDateTime joinedAt;
    private LocalDate customStart;
    private LocalDate customEnd;

    // Campos do CourseModel
    private UUID templateId;
    private String name;
    private String level;
    private String divisionType;
    private int divisionsCount;
    private String institutionName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String address;
    private boolean online;
    private String status;
    private List<String> phones;
    private List<String> emails;
    private UUID createdBy;
}