package com.focados.foca.modules.tasks.infra.http.controller;

import com.focados.foca.modules.tasks.domain.dtos.request.CompleteTaskDTO;
import com.focados.foca.modules.tasks.domain.dtos.request.CreateTaskDTO;
import com.focados.foca.modules.tasks.domain.dtos.request.InviteCollaboratorDTO;
import com.focados.foca.modules.tasks.domain.dtos.request.UpdateTaskDTO;
import com.focados.foca.modules.tasks.domain.dtos.response.CompleteTaskResponseDTO;
import com.focados.foca.modules.tasks.domain.dtos.response.TaskResponseDTO;
import com.focados.foca.modules.tasks.domain.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @PostMapping
    public TaskResponseDTO create(@RequestBody CreateTaskDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<TaskResponseDTO> getAll() {
        return service.findAll();
    }

    @GetMapping("/by-discipline/{disciplineInstanceId}")
    public List<TaskResponseDTO> getByDiscipline(@PathVariable UUID disciplineInstanceId) {
        return service.findAllByDisciplineInstance(disciplineInstanceId);
    }

    @GetMapping("/{id}")
    public TaskResponseDTO getOne(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public TaskResponseDTO update(@PathVariable UUID id, @RequestBody UpdateTaskDTO dto) {
        return service.updateTask(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.deleteTask(id);
    }

    @PutMapping("/complete/{id}")
    public CompleteTaskResponseDTO toggleCompleted(
            @PathVariable UUID id,
            @RequestBody CompleteTaskDTO dto
    ) {
        return service.markTaskComplete(id, dto);
    }

}