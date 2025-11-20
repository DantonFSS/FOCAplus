package com.focados.foca.modules.assessments.database.entity;

import com.focados.foca.modules.materias.database.entity.DisciplineInstanceModel;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "assessments")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "discipline_instance_id", nullable = false)
    private DisciplineInstanceModel disciplineInstance;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(name = "points_possible")
    private Integer pointsPossible = 0;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "grade")
    private Integer grade;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime createdAt = ZonedDateTime.now();

    @OneToMany(mappedBy = "assessment", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<AssessmentCollaboratorModel> collaborators = new ArrayList<>();
}