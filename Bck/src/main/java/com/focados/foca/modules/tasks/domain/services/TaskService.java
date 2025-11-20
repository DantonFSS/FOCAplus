package com.focados.foca.modules.tasks.domain.services;

import com.focados.foca.modules.courses.database.entity.enums.UserCourseRole;
import com.focados.foca.modules.courses.domain.services.ShareCodeUtil;
import com.focados.foca.modules.materias.database.repository.DisciplineInstanceRepository;
import com.focados.foca.modules.score.database.entity.ScoreRecordModel;
import com.focados.foca.modules.score.domain.dtos.mappers.ScoreRecordMapper;
import com.focados.foca.modules.score.domain.services.ScoreRecordService;
import com.focados.foca.modules.tasks.database.entity.TaskCollaboratorModel;
import com.focados.foca.modules.tasks.database.entity.TaskModel;
import com.focados.foca.modules.tasks.database.repository.TaskCollaboratorRepository;
import com.focados.foca.modules.tasks.database.repository.TaskRepository;
import com.focados.foca.modules.tasks.domain.dtos.mappers.TaskMapper;
import com.focados.foca.modules.tasks.domain.dtos.request.CompleteTaskDTO;
import com.focados.foca.modules.tasks.domain.dtos.request.CreateTaskDTO;
import com.focados.foca.modules.tasks.domain.dtos.request.UpdateTaskDTO;
import com.focados.foca.modules.tasks.domain.dtos.response.CompleteTaskResponseDTO;
import com.focados.foca.modules.tasks.domain.dtos.response.TaskCollaboratorResponseDTO;
import com.focados.foca.modules.tasks.domain.dtos.response.TaskResponseDTO;
import com.focados.foca.modules.users.database.repository.UserRepository;
import com.focados.foca.modules.users.domain.services.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final DisciplineInstanceRepository instanceRepository;
    private final TaskMapper mapper;
    private final ScoreRecordService scoreRecordService;
    private final ScoreRecordMapper scoreRecordMapper;
    private final UserRepository userRepository;
    private final TaskCollaboratorRepository collaboratorRepository;

    public TaskResponseDTO create(CreateTaskDTO dto) {
        var instance = instanceRepository.findById(dto.disciplineInstanceId())
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));
        TaskModel entity = mapper.toEntity(dto, instance);

        entity = taskRepository.save(entity);

        var owner = instance.getUserCourse().getUser();
        var collabOwner = new TaskCollaboratorModel();
        collabOwner.setTask(entity);
        collabOwner.setUser(owner);
        collabOwner.setRole(UserCourseRole.OWNER);
        collaboratorRepository.save(collabOwner);

        return mapper.toResponse(entity);
    }

    public List<TaskResponseDTO> findAll() {
        return taskRepository.findAll().stream().map(mapper::toResponse).toList();
    }

    public List<TaskResponseDTO> findAllByDisciplineInstance(UUID disciplineInstanceId) {
        return taskRepository.findAllByDisciplineInstanceId(disciplineInstanceId)
                .stream().map(mapper::toResponse).toList();
    }

    public TaskResponseDTO findById(UUID id) {
        var entity = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task não encontrada"));
        return mapper.toResponse(entity);
    }

    public TaskResponseDTO updateTask(UUID id, UpdateTaskDTO dto) {
        var entity = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task não encontrada"));
        mapper.updateEntity(entity, dto);
        entity = taskRepository.save(entity);
        return mapper.toResponse(entity);
    }

    public void deleteTask(UUID id) {
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("Task não encontrada");
        }
        taskRepository.deleteById(id);
    }

    public CompleteTaskResponseDTO markTaskComplete(UUID taskId, CompleteTaskDTO dto) {
        UUID userId = AuthUtil.getAuthenticatedUserId();
        var entity = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task não encontrada"));
        boolean wasCompleted = entity.isCompleted();

        entity.setCompleted(dto.completed());
        entity.setCompletedAt(dto.completed() ? java.time.ZonedDateTime.now() : null);
        taskRepository.save(entity);

        ScoreRecordModel score = null;
        if (!wasCompleted && dto.completed()) {
            score = scoreRecordService.onTaskCompleted(entity, userId);
        } else if (wasCompleted && !dto.completed()) {
            scoreRecordService.onTaskUncompleted(entity);
        }
        return new CompleteTaskResponseDTO(
                mapper.toResponse(entity),
                scoreRecordMapper.toDTO(score)
        );
    }


}