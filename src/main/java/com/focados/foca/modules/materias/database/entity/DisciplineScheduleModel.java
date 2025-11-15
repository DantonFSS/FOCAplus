package com.focados.foca.modules.materias.database.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "discipline_schedules")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisciplineScheduleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "discipline_instance_id")
    private DisciplineInstanceModel disciplineInstance;

    @Column(nullable = false)
    private int weekday; // 0 = domingo, 6 = sábado

    @Column(nullable = false)
    private LocalTime startTime;

    @Column
    private LocalTime endTime;
}