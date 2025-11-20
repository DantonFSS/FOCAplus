package com.focados.foca.modules.tasks.database.repository;

import com.focados.foca.modules.tasks.database.entity.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<TaskModel, UUID> {
    List<TaskModel> findAllByDisciplineInstanceId(UUID disciplineInstanceId);
}