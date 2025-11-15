package com.focados.foca.modules.materias.infra.http.controller;

import com.focados.foca.modules.materias.domain.dtos.request.CreateDisciplineScheduleDTO;
import com.focados.foca.modules.materias.domain.dtos.request.UpdateDisciplineScheduleDTO;
import com.focados.foca.modules.materias.domain.dtos.response.DisciplineScheduleResponseDTO;
import com.focados.foca.modules.materias.domain.services.DisciplineScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/discipline-schedules")
@RequiredArgsConstructor
public class DisciplineScheduleController {

    private final DisciplineScheduleService service;

    @PostMapping
    public DisciplineScheduleResponseDTO addSchedule(
            @RequestBody CreateDisciplineScheduleDTO dto
    ) {
        return service.addSchedule(dto);
    }

    @GetMapping
    public List<DisciplineScheduleResponseDTO> getAll() {
        return service.getAllSchedules();
    }

    @GetMapping("/by-discipline/{disciplineInstanceId}")
    public List<DisciplineScheduleResponseDTO> getByDisciplineInstanceId(@PathVariable UUID disciplineInstanceId) {
        return service.getSchedulesByDisciplineInstanceId(disciplineInstanceId);
    }

    @GetMapping("/{id}")
    public DisciplineScheduleResponseDTO getOne(@PathVariable UUID id) {
        return service.getOneById(id);
    }

    @PutMapping("/{id}")
    public DisciplineScheduleResponseDTO updateSchedule(
            @PathVariable UUID id,
            @RequestBody UpdateDisciplineScheduleDTO dto
    ) {
        return service.updateSchedule(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable UUID id) {
        service.deleteSchedule(id);
    }
}