package com.focados.foca.modules.materias.infra.http.controller;

import com.focados.foca.modules.materias.domain.dtos.request.CreateDisciplineInstanceDto;
import com.focados.foca.modules.materias.domain.dtos.request.UpdateDisciplineInstanceDto;
import com.focados.foca.modules.materias.domain.dtos.response.DisciplineInstanceResponseDto;
import com.focados.foca.modules.materias.domain.services.DisciplineInstanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/discipline-instances")
@RequiredArgsConstructor
public class DisciplineInstanceController {

    private final DisciplineInstanceService service;

    @PostMapping
    public ResponseEntity<DisciplineInstanceResponseDto> create(@RequestBody CreateDisciplineInstanceDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<DisciplineInstanceResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/by-period/{periodInstanceId}")
    public ResponseEntity<List<DisciplineInstanceResponseDto>> getAllByPeriodInstance(
            @PathVariable UUID periodInstanceId
    ) {
        return ResponseEntity.ok(service.getAllByPeriodInstanceId(periodInstanceId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplineInstanceResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisciplineInstanceResponseDto> update(
            @PathVariable UUID id,
            @RequestBody UpdateDisciplineInstanceDto dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}