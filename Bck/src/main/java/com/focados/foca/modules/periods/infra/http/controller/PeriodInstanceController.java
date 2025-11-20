package com.focados.foca.modules.periods.infra.http.controller;

import com.focados.foca.modules.periods.domain.dtos.request.CreatePeriodInstanceDto;
import com.focados.foca.modules.periods.domain.dtos.request.UpdatePeriodInstanceDto;
import com.focados.foca.modules.periods.domain.dtos.response.PeriodInstanceResponseDto;
import com.focados.foca.modules.periods.domain.services.PeriodInstanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/period-instances")
@RequiredArgsConstructor
public class PeriodInstanceController {

    private final PeriodInstanceService service;

    @PostMapping
    public ResponseEntity<PeriodInstanceResponseDto> create(@RequestBody CreatePeriodInstanceDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<PeriodInstanceResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/by-user-course/{userCourseId}")
    public ResponseEntity<List<PeriodInstanceResponseDto>> getAllByUserCourseId(@PathVariable UUID userCourseId) {
        return ResponseEntity.ok(service.getAllByUserCourseId(userCourseId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeriodInstanceResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeriodInstanceResponseDto> update(
            @PathVariable UUID id,
            @RequestBody UpdatePeriodInstanceDto dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}