package com.focados.foca.modules.studySessions.database.entity;

import com.focados.foca.modules.studySessions.database.entity.enums.StudySessionMode;
import com.focados.foca.modules.studySessions.database.entity.enums.StudySessionType;
import com.focados.foca.modules.users.database.entity.UserModel;
import com.focados.foca.modules.courses.database.entity.UserCourseModel;
import com.focados.foca.modules.materias.database.entity.DisciplineInstanceModel;
import jakarta.persistence.*;
import lombok.*;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Entidade do módulo StudySessions
 */
@Entity
@Table(name = "study_sessions")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudySessionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_course_id", nullable = false)
    private UserCourseModel userCourse;

    @ManyToOne
    @JoinColumn(name = "discipline_instance_id")
    private DisciplineInstanceModel disciplineInstance;

    @Enumerated(EnumType.STRING)
    @Column(name = "session_type", nullable = false)
    private StudySessionType sessionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode")
    private StudySessionMode mode;

    @Column(name = "duration_seconds")
    private Integer durationSeconds = 0;

    @Column(name = "pomodoro_cycles")
    private Integer pomodoroCycles = 0;

    @Column(name = "points_earned")
    private Integer pointsEarned = 0;

    @Column(name = "started_at")
    private ZonedDateTime startedAt = ZonedDateTime.now();

    @Column(name = "ended_at")
    private ZonedDateTime endedAt;
}