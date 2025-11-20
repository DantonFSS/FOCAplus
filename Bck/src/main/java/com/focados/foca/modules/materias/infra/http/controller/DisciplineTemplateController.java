package com.focados.foca.modules.materias.infra.http.controller;

import com.focados.foca.modules.materias.domain.dtos.request.BatchCreateDisciplineTemplateDto;
import com.focados.foca.modules.materias.domain.dtos.request.CreateDisciplineTemplateDto;
import com.focados.foca.modules.materias.domain.dtos.request.UpdateDisciplineTemplateDto;
import com.focados.foca.modules.materias.domain.dtos.response.DisciplineTemplateResponseDto;
import com.focados.foca.modules.materias.domain.services.DisciplineTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/discipline-templates")
@RequiredArgsConstructor
public class DisciplineTemplateController {

    private final DisciplineTemplateService service;

    @PostMapping
    public ResponseEntity<DisciplineTemplateResponseDto> create(@RequestBody CreateDisciplineTemplateDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<DisciplineTemplateResponseDto>> batchCreate(
            @RequestBody BatchCreateDisciplineTemplateDto dto
    ) {
        return ResponseEntity.ok(service.batchCreate(dto));
    }

    @GetMapping
    public ResponseEntity<List<DisciplineTemplateResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/by-period/{periodTemplateId}")
    public ResponseEntity<List<DisciplineTemplateResponseDto>> getAllByPeriod(@PathVariable UUID periodTemplateId) {
        return ResponseEntity.ok(service.getAllByPeriodTemplateId(periodTemplateId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplineTemplateResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisciplineTemplateResponseDto> update(
            @PathVariable UUID id,
            @RequestBody UpdateDisciplineTemplateDto dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}