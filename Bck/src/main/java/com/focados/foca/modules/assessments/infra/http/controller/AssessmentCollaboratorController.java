package com.focados.foca.modules.assessments.infra.http.controller;

import com.focados.foca.modules.assessments.domain.dtos.request.InviteAssessmentCollaboratorDTO;
import com.focados.foca.modules.assessments.domain.dtos.response.AssessmentCollaboratorResponseDTO;
import com.focados.foca.modules.assessments.domain.services.AssessmentCollaboratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/assessments/collaborators")
@RequiredArgsConstructor
public class AssessmentCollaboratorController {
    private final AssessmentCollaboratorService service;

    @GetMapping("/{assessmentId}")
    public ResponseEntity<List<AssessmentCollaboratorResponseDTO>> getCollaborators(
            @PathVariable UUID assessmentId
    ) {
        return ResponseEntity.ok(service.getCollaboratorsOfAssessment(assessmentId));
    }

    @PostMapping("/{assessmentId}")
    public ResponseEntity<Void> inviteCollaborator(
            @PathVariable UUID assessmentId,
            @RequestBody InviteAssessmentCollaboratorDTO dto
    ) {
        service.inviteUserToAssessment(assessmentId, dto.getUsername());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{assessmentId}/{userId}")
    public ResponseEntity<Void> removeCollaborator(
            @PathVariable UUID assessmentId,
            @PathVariable UUID userId
    ) {
        service.removeUserFromAssessment(assessmentId, userId);
        return ResponseEntity.noContent().build();
    }
}