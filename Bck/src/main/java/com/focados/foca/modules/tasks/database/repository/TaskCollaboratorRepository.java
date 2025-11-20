package com.focados.foca.modules.tasks.database.repository;

import com.focados.foca.modules.tasks.database.entity.TaskCollaboratorModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskCollaboratorRepository extends JpaRepository<TaskCollaboratorModel, UUID> {
    Optional<TaskCollaboratorModel> findByTaskIdAndUserId(UUID taskId, UUID userId);
    List<TaskCollaboratorModel> findAllByTaskId(UUID taskId);
    void deleteByTaskIdAndUserId(UUID taskId, UUID userId);
}