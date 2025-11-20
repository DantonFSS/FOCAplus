package com.focados.foca.modules.periods.infra.http.controller;

import com.focados.foca.modules.periods.domain.dtos.request.CreatePeriodTemplateDto;
import com.focados.foca.modules.periods.domain.dtos.request.UpdatePeriodTemplateDto;
import com.focados.foca.modules.periods.domain.dtos.response.PeriodTemplateResponseDto;
import com.focados.foca.modules.periods.domain.services.PeriodTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/period-templates")
@RequiredArgsConstructor
public class PeriodTemplateController {

    private final PeriodTemplateService service;

    @PostMapping
    public ResponseEntity<PeriodTemplateResponseDto> addSemester(@RequestBody CreatePeriodTemplateDto dto) {
        return ResponseEntity.ok(service.addSemester(dto));
    }

    @GetMapping
    public ResponseEntity<List<PeriodTemplateResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/by-course/{courseTemplateId}")
    public ResponseEntity<List<PeriodTemplateResponseDto>> getAllByCourseId(@PathVariable UUID courseTemplateId) {
        return ResponseEntity.ok(service.getAllByCourseId(courseTemplateId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeriodTemplateResponseDto> getOne(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeriodTemplateResponseDto> updateDates(
            @PathVariable UUID id,
            @RequestBody UpdatePeriodTemplateDto dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}