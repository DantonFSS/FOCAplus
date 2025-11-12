package com.focados.foca.modules.materias.database.entity;

import com.focados.foca.modules.periods.database.entity.PeriodTemplateModel;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "discipline_templates")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisciplineTemplateModel {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "period_template_id")
    private PeriodTemplateModel periodTemplate;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String notes;
}