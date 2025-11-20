package com.focados.foca.modules.assessments.database.entity;

import com.focados.foca.modules.courses.database.entity.enums.UserCourseRole;
import com.focados.foca.modules.users.database.entity.UserModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "assessment_collaborators")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentCollaboratorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "assessment_id", nullable = false)
    private AssessmentModel assessment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserCourseRole role;
}