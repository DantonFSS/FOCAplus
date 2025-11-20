package com.focados.foca.modules.tasks.database.entity;

import com.focados.foca.modules.courses.database.entity.enums.UserCourseRole;
import com.focados.foca.modules.users.database.entity.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "task_collaborators")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskCollaboratorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private TaskModel task;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserCourseRole role;
}