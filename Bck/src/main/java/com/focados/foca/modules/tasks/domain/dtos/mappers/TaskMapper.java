package com.focados.foca.modules.tasks.domain.dtos.mappers;

import com.focados.foca.modules.materias.database.entity.DisciplineInstanceModel;
import com.focados.foca.modules.tasks.database.entity.TaskModel;
import com.focados.foca.modules.tasks.domain.dtos.request.CreateTaskDTO;
import com.focados.foca.modules.tasks.domain.dtos.request.UpdateTaskDTO;
import com.focados.foca.modules.tasks.domain.dtos.response.TaskResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskModel toEntity(CreateTaskDTO dto, DisciplineInstanceModel instance) {
        TaskModel model = new TaskModel();
        model.setDisciplineInstance(instance);
        model.setTitle(dto.title());
        model.setDescription(dto.description());
        model.setPointsPossible(dto.pointsPossible() != null ? dto.pointsPossible() : 0);
        model.setDueDate(dto.dueDate());
        model.setCompleted(false);
        model.setCompletedAt(null);
        return model;
    }

    public void updateEntity(TaskModel model, UpdateTaskDTO dto) {
        if (dto.title() != null) model.setTitle(dto.title());
        if (dto.description() != null) model.setDescription(dto.description());
        if (dto.pointsPossible() != null) model.setPointsPossible(dto.pointsPossible());
        if (dto.dueDate() != null) model.setDueDate(dto.dueDate());
        if (dto.completed() != null) {
            model.setCompleted(dto.completed());
            if (dto.completed()) {
                model.setCompletedAt(java.time.ZonedDateTime.now());
            } else {
                model.setCompletedAt(null);
            }
        }
    }

    public TaskResponseDTO toResponse(TaskModel model) {
        return new TaskResponseDTO(
                model.getId(),
                model.getDisciplineInstance().getId(),
                model.getTitle(),
                model.getDescription(),
                model.getPointsPossible(),
                model.getDueDate(),
                model.getCreatedAt(),
                model.isCompleted(),
                model.getCompletedAt()
        );
    }
}