package com.focados.foca.modules.tasks.domain.dtos.response;

import com.focados.foca.modules.courses.database.entity.enums.UserCourseRole;
import lombok.Data;
import java.util.UUID;

@Data
public class TaskCollaboratorResponseDTO {
    private UUID userId;
    private String name;
    private String username;
    private UserCourseRole role;
}