package com.focados.foca.modules.assessments.domain.dtos.response;

import com.focados.foca.modules.courses.database.entity.enums.UserCourseRole;
import lombok.Data;
import java.util.UUID;

@Data
public class AssessmentCollaboratorResponseDTO {
    private UUID userId;
    private String name;
    private String username;
    private UserCourseRole role;
}