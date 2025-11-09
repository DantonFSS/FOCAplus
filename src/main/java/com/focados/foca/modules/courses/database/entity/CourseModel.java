package com.focados.foca.modules.courses.database.entity;

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

    @Column(name = "status")
    private String status = "not_started";

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "phones")
    private List<String> phones;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "emails")
    private List<String> emails;

    @OneToMany(mappedBy = "courseTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCourseModel> userCourses;
}

