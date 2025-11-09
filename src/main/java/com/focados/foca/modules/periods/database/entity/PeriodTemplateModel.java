package com.focados.foca.modules.periods.database.entity;

import com.focados.foca.modules.courses.database.entity.CourseModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "period_templates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodTemplateModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_template_id", nullable = false)
    private CourseModel courseTemplate;

    private String name;
    private Integer position;
    private LocalDate plannedStart;
    private LocalDate plannedEnd;

    @CreationTimestamp
    private ZonedDateTime createdAt;
}