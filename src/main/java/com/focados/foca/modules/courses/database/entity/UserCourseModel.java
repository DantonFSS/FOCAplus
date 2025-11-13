package com.focados.foca.modules.courses.database.entity;

import com.focados.foca.modules.courses.database.entity.enums.UserCourseRole;
import com.focados.foca.modules.users.database.entity.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCourseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_template_id", nullable = false)
    private CourseModel courseTemplate;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserCourseRole role = UserCourseRole.OWNER;

    @Column(name = "accepted")
    private boolean accepted = false;

    @CreationTimestamp
    @Column(name = "joined_at", updatable = false)
    private ZonedDateTime joinedAt;

    @Column(name = "custom_start")
    private LocalDate customStart;

    @Column(name = "custom_end")
    private LocalDate customEnd;
}
