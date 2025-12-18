package com.example.taskmanager.task.domain;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);

    List<Task> findByProjectId(Long projectId);

    Optional<Task> findByIdAndProjectId(Long taskId, Long projectId);

    void deleteByIdAndProjectId(Long taskId, Long projectId);
}


