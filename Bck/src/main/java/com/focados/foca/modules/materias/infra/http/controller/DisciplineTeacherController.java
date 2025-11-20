package com.focados.foca.modules.materias.infra.http.controller;

import com.focados.foca.modules.materias.domain.dtos.request.CreateDisciplineTeacherDTO;
import com.focados.foca.modules.materias.domain.dtos.request.UpdateDisciplineTeacherDTO;
import com.focados.foca.modules.materias.domain.dtos.response.DisciplineTeacherResponseDTO;
import com.focados.foca.modules.materias.domain.services.DisciplineTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/discipline-teachers")
@RequiredArgsConstructor
public class DisciplineTeacherController {

    private final DisciplineTeacherService service;

    @PostMapping
    public DisciplineTeacherResponseDTO addTeacher(@RequestBody CreateDisciplineTeacherDTO dto) {
        return service.addTeacher(dto);
    }

    @GetMapping
    public List<DisciplineTeacherResponseDTO> getAll() {
        return service.getAllTeachers();
    }

    @GetMapping("/by-discipline/{disciplineInstanceId}")
    public List<DisciplineTeacherResponseDTO> getByDisciplineInstanceId(
            @PathVariable UUID disciplineInstanceId) {
        return service.getByDisciplineInstanceId(disciplineInstanceId);
    }

    @GetMapping("/{id}")
    public DisciplineTeacherResponseDTO getOne(@PathVariable UUID id) {
        return service.getOneById(id);
    }

    @PutMapping("/{id}")
    public DisciplineTeacherResponseDTO updateTeacher(
            @PathVariable UUID id,
            @RequestBody UpdateDisciplineTeacherDTO dto
    ) {
        return service.updateTeacher(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable UUID id) {
        service.deleteTeacher(id);
    }
}