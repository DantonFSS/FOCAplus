package com.focados.foca.modules.materias.database.entity;

import com.focados.foca.modules.courses.database.entity.UserCourseModel;
import com.focados.foca.modules.materias.database.entity.enums.DisciplineStatus;
import com.focados.foca.modules.materias.database.entity.enums.GradeSystem;
import com.focados.foca.modules.periods.database.entity.PeriodInstanceModel;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "discipline_instances")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisciplineInstanceModel {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_course_id")
    private UserCourseModel userCourse;

    @ManyToOne(optional = true)
    @JoinColumn(name = "discipline_template_id")
    private DisciplineTemplateModel disciplineTemplate;

    @ManyToOne(optional = true)
    @JoinColumn(name = "period_instance_id")
    private PeriodInstanceModel periodInstance;

    private LocalDate plannedStart;
    private LocalDate plannedEnd;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DisciplineStatus status = DisciplineStatus.NOT_STARTED;

    private BigDecimal grade;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade_system", nullable = false)
    private GradeSystem gradeSystem = GradeSystem.NUMERIC_10;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime createdAt = ZonedDateTime.now();

    @Column(name = "assessments_count")
    private Integer assessmentsCount;
}