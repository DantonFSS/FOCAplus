package com.focados.foca.modules.tasks.database.entity;

import com.focados.foca.modules.materias.database.entity.DisciplineInstanceModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskModel {
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

    @Column(name = "due_date")
    private ZonedDateTime dueDate;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private ZonedDateTime createdAt;

    @Column(name = "completed")
    private boolean completed = false;

    @Column(name = "completed_at")
    private ZonedDateTime completedAt;

    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<TaskCollaboratorModel> collaborators = new ArrayList<>();
}