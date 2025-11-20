package com.focados.foca.modules.selfEvaluation.infra.http.controller;

import com.focados.foca.modules.selfEvaluation.domain.dtos.request.SelfEvalAnswerRequestDTO;
import com.focados.foca.modules.selfEvaluation.domain.dtos.response.SelfEvalAnswerResponseDTO;
import com.focados.foca.modules.selfEvaluation.domain.services.SelfEvalAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/self-eval-answers")
@RequiredArgsConstructor
public class SelfEvalAnswerController {
    private final SelfEvalAnswerService service;

    @PostMapping
    public ResponseEntity<SelfEvalAnswerResponseDTO> create(@RequestBody SelfEvalAnswerRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<SelfEvalAnswerResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/by-question/{questionId}")
    public ResponseEntity<List<SelfEvalAnswerResponseDTO>> getAllByQuestion(@PathVariable UUID questionId) {
        return ResponseEntity.ok(service.getAllByQuestionId(questionId));
    }

    @GetMapping("/by-session/{sessionId}")
    public ResponseEntity<List<SelfEvalAnswerResponseDTO>> getAllBySession(@PathVariable UUID sessionId) {
        return ResponseEntity.ok(service.getAllBySessionId(sessionId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SelfEvalAnswerResponseDTO> getOne(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SelfEvalAnswerResponseDTO> update(
            @PathVariable UUID id, @RequestBody SelfEvalAnswerRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}