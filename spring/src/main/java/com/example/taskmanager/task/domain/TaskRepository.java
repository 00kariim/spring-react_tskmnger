package com.example.taskmanager.task.domain;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);

    List<Task> findByProjectId(Long projectId);

    Optional<Task> findByIdAndProjectId(Long taskId, Long projectId);

    void deleteByIdAndProjectId(Long taskId, Long projectId);

    // Search tasks within a project. `query` may be null/empty to match all; `completed` may be null to not filter by completion.
    List<Task> search(Long projectId, String query, Boolean completed);

    // Pageable listing support
    org.springframework.data.domain.Page<Task> findByProjectId(Long projectId, org.springframework.data.domain.Pageable pageable);
}


