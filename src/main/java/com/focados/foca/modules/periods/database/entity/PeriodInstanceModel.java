package com.focados.foca.modules.periods.database.entity;


import com.focados.foca.modules.courses.database.entity.UserCourseModel;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "period_instances")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodInstanceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_course_id", nullable = false)
    private UserCourseModel userCourse;

    @ManyToOne(optional = true)
    @JoinColumn(name = "period_template_id", nullable = true)
    private PeriodTemplateModel periodTemplate;

    @Column(name = "name")
    private String name;

    @Column(name = "position")
    private Integer position;

    @Column(name = "planned_start")
    private LocalDate plannedStart;

    @Column(name = "planned_end")
    private LocalDate plannedEnd;
}