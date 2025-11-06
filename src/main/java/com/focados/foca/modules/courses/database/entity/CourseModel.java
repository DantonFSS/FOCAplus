package com.focados.foca.modules.courses.database.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Entidade do módulo Courses
 */
@Entity
@Table(name = "course_templates")
public class CourseModel {


    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String level;

    @Column(name = "division_type", nullable = false)
    private String divisionType;

    @Column(name = "divisions_count", nullable = false)
    private int divisionsCount;

    @Column(name = "institution_name")
    private String institutionName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    private String address;

    @Column(name = "is_online")
    private boolean isOnline = false;

    @Column(name = "created_by")
    private UUID createdBy;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private ZonedDateTime createdAt;

    //@Enumerated(EnumType.STRING)
    //private CourseStatus status = CourseStatus.NOT_STARTED;

    @ElementCollection
    @Column(name = "phones")
    private List<String> phones;

    @ElementCollection
    @Column(name = "emails")
    private List<String> emails;

}

