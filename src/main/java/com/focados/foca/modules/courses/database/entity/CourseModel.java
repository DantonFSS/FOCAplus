package com.focados.foca.modules.courses.database.entity;

import com.focados.foca.modules.courses.database.entity.enums.CourseLevel;
import com.focados.foca.modules.courses.database.entity.enums.CourseStatus;
import com.focados.foca.modules.courses.database.entity.enums.DivisionType;
import com.focados.foca.modules.users.database.entity.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseLevel level;

    @Enumerated(EnumType.STRING)
    @Column(name = "division_type", nullable = false)
    private DivisionType divisionType = DivisionType.PERIOD;

    @Column(name = "divisions_count", nullable = false)
    private int divisionsCount = 1;

    @Column(name = "institution_name")
    private String institutionName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "expected_end_date")
    private LocalDate endDate;

    private String address;

    @Column(name = "is_online")
    private boolean online = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private UserModel createdBy;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private ZonedDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CourseStatus status = CourseStatus.NOT_STARTED;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "phones")
    private List<String> phones;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "emails")
    private List<String> emails;

    @Column(name = "share_code", unique = true, nullable = false)
    private String shareCode;

    @OneToMany(mappedBy = "courseTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCourseModel> userCourses;
}

