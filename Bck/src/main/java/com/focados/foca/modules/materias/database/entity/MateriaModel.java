package com.focados.foca.modules.materias.database.entity;

import com.focados.foca.modules.courses.database.entity.CourseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Entidade do módulo Materias (Subjects)
 */
@Entity
@Table(name = "subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MateriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseModel course;

    @Column(nullable = false)
    private String name;

    @Column(name = "credit_info")
    private String creditInfo;
}

