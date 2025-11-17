package com.focados.foca.modules.studySessions.infra.http.controller;

import com.focados.foca.modules.studySessions.domain.dtos.request.CreateStudySessionDTO;
import com.focados.foca.modules.studySessions.domain.dtos.response.StudySessionResponseDTO;
import com.focados.foca.modules.studySessions.domain.services.StudySessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/study-sessions")
@RequiredArgsConstructor
public class StudySessionController {
    private final StudySessionService service;

    @PostMapping
    public ResponseEntity<StudySessionResponseDTO> createStudySession(
            @RequestBody CreateStudySessionDTO dto
    ) {
        var response = service.createSession(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<StudySessionResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/by-discipline/{disciplineInstanceId}")
    public ResponseEntity<List<StudySessionResponseDTO>> getAllByDiscipline(@PathVariable UUID disciplineInstanceId) {
        return ResponseEntity.ok(service.getAllByDiscipline(disciplineInstanceId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudySessionResponseDTO> getOne(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable UUID id) {
        service.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}