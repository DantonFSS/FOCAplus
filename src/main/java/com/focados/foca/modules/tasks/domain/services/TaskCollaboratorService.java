package com.focados.foca.modules.tasks.domain.services;

import com.focados.foca.modules.courses.database.entity.enums.UserCourseRole;
import com.focados.foca.modules.tasks.database.entity.TaskCollaboratorModel;
import com.focados.foca.modules.tasks.database.repository.TaskCollaboratorRepository;
import com.focados.foca.modules.tasks.database.repository.TaskRepository;
import com.focados.foca.modules.users.database.repository.UserRepository;
import com.focados.foca.modules.tasks.domain.dtos.response.TaskCollaboratorResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskCollaboratorService {
    private final TaskCollaboratorRepository collaboratorRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public void inviteUserToTask(UUID taskId, String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));

        boolean alreadyCollaborator = collaboratorRepository.findByTaskIdAndUserId(taskId, user.getId()).isPresent();
        if (alreadyCollaborator) return;

        var collab = new TaskCollaboratorModel();
        collab.setTask(task);
        collab.setUser(user);
        collab.setRole(UserCourseRole.MEMBER);
        collaboratorRepository.save(collab);
    }

    public List<TaskCollaboratorResponseDTO> getCollaboratorsOfTask(UUID taskId) {
        var collaborators = collaboratorRepository.findAllByTaskId(taskId);
        return collaborators.stream().map(collab -> {
            var dto = new TaskCollaboratorResponseDTO();
            dto.setUserId(collab.getUser().getId());
            dto.setName(collab.getUser().getName());
            dto.setUsername(collab.getUser().getUsername());
            dto.setRole(collab.getRole());
            return dto;
        }).toList();
    }

    @Transactional
    public void removeUserFromTask(UUID taskId, UUID userId) {
        var exists = collaboratorRepository.findByTaskIdAndUserId(taskId, userId).isPresent();
        if (!exists) {
            throw new IllegalArgumentException("Colaborador não encontrado para esta task");
        }
        collaboratorRepository.deleteByTaskIdAndUserId(taskId, userId);
    }

/*
    public void joinTaskByShareCode(String shareCode, UUID userId) {
        var task = taskRepository.findByShareCode(shareCode)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        var collab = new TaskCollaboratorModel();
        collab.setTask(task);
        collab.setUser(user);
        collab.setRole("member");
        collaboratorRepository.save(collab);
    }*/
}