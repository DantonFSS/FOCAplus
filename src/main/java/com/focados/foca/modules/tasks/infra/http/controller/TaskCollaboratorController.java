package com.focados.foca.modules.tasks.infra.http.controller;

import com.focados.foca.modules.tasks.domain.dtos.request.InviteCollaboratorDTO;
import com.focados.foca.modules.tasks.domain.dtos.response.TaskCollaboratorResponseDTO;
import com.focados.foca.modules.tasks.domain.services.TaskCollaboratorService;
import com.focados.foca.modules.tasks.domain.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks/collaborators/{taskId}")
@RequiredArgsConstructor
public class TaskCollaboratorController {
    private final TaskCollaboratorService collaboratorService;

    @PostMapping
    public ResponseEntity<Void> inviteCollaborator(
            @PathVariable UUID taskId,
            @RequestBody InviteCollaboratorDTO dto
    ) {
        collaboratorService.inviteUserToTask(taskId, dto.getUsername());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TaskCollaboratorResponseDTO>> getCollaborators(@PathVariable UUID taskId) {
        var collabs = collaboratorService.getCollaboratorsOfTask(taskId);
        return ResponseEntity.ok(collabs);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeCollaborator(
            @PathVariable UUID taskId,
            @PathVariable UUID userId
    ) {
        collaboratorService.removeUserFromTask(taskId, userId);
        return ResponseEntity.noContent().build();
    }
}