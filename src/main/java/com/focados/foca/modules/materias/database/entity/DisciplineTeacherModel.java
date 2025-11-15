package com.focados.foca.modules.materias.database.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "discipline_teachers")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisciplineTeacherModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "discipline_instance_id")
    private DisciplineInstanceModel disciplineInstance;

    @Column(nullable = false)
    private String teacherName;
}