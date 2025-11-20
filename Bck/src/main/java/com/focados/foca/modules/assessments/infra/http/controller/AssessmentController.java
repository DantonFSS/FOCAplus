package com.focados.foca.modules.assessments.infra.http.controller;

import com.focados.foca.modules.assessments.domain.dtos.request.CreateAssessmentDTO;
import com.focados.foca.modules.assessments.domain.dtos.request.UpdateAssessmentDTO;
import com.focados.foca.modules.assessments.domain.dtos.response.AssessmentResponseDTO;
import com.focados.foca.modules.assessments.domain.services.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/assessments")
@RequiredArgsConstructor
public class AssessmentController {
    private final AssessmentService assessmentService;

    @PostMapping
    public ResponseEntity<AssessmentResponseDTO> createAssessment(
            @RequestBody CreateAssessmentDTO dto
    ) {
        var responseDto = assessmentService.createAssessment(dto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<AssessmentResponseDTO>> getAllAssessments() {
        return ResponseEntity.ok(assessmentService.getAllAssessments());
    }

    // GET by discipline instance
    @GetMapping("/by-discipline/{disciplineInstanceId}")
    public ResponseEntity<List<AssessmentResponseDTO>> getByDiscipline(@PathVariable UUID disciplineInstanceId) {
        return ResponseEntity.ok(assessmentService.getByDisciplineInstance(disciplineInstanceId));
    }

    // GET one
    @GetMapping("/{id}")
    public ResponseEntity<AssessmentResponseDTO> getOne(@PathVariable UUID id) {
        return ResponseEntity.ok(assessmentService.getOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssessmentResponseDTO> updateAssessment(
            @PathVariable UUID id,
            @RequestBody UpdateAssessmentDTO dto
    ) {
        return ResponseEntity.ok(assessmentService.updateAssessment(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssessment(@PathVariable UUID id) {
        assessmentService.deleteAssessment(id);
        return ResponseEntity.noContent().build();
    }
}