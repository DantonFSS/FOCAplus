package com.focados.foca.modules.selfEvaluation.infra.http.controller;

import com.focados.foca.modules.selfEvaluation.domain.dtos.request.SelfEvalQuestionRequestDTO;
import com.focados.foca.modules.selfEvaluation.domain.dtos.response.SelfEvalQuestionResponseDTO;
import com.focados.foca.modules.selfEvaluation.domain.services.SelfEvalQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/self-eval-questions")
@RequiredArgsConstructor
public class SelfEvalQuestionController {
    private final SelfEvalQuestionService service;

    @PostMapping
    public ResponseEntity<SelfEvalQuestionResponseDTO> create(@RequestBody SelfEvalQuestionRequestDTO dto) {
        return ResponseEntity.ok(service.createQuestion(dto));
    }

    @GetMapping
    public ResponseEntity<List<SelfEvalQuestionResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SelfEvalQuestionResponseDTO> getOne(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SelfEvalQuestionResponseDTO> update(
            @PathVariable UUID id, @RequestBody SelfEvalQuestionRequestDTO dto) {
        return ResponseEntity.ok(service.updateQuestion(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}