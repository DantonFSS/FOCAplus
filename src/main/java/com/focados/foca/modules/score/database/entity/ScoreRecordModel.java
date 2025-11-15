package com.focados.foca.modules.score.database.entity;

import com.focados.foca.modules.courses.database.entity.UserCourseModel;
import com.focados.foca.modules.materias.database.entity.DisciplineInstanceModel;
import com.focados.foca.modules.score.database.entity.enums.ScoreSourceType;
import com.focados.foca.modules.users.database.entity.UserModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "score_records")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScoreRecordModel {
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
    @Column(name = "source_type", nullable = false)
    private ScoreSourceType sourceType;

    @Column(name = "source_id")
    private UUID sourceId;

    @Column(name = "points", nullable = false)
    private Integer points;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private ZonedDateTime createdAt;

}