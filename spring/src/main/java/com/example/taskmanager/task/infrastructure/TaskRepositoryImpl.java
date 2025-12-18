package com.example.taskmanager.task.infrastructure;

import com.example.taskmanager.task.domain.Task;
import com.example.taskmanager.task.domain.TaskId;
import com.example.taskmanager.task.domain.TaskRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private final TaskJpaRepository jpaRepository;

    public TaskRepositoryImpl(TaskJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Task save(Task task) {
        TaskJpaEntity entity;
        if (task.getId() != null) {
            // Update existing entity
            entity = jpaRepository.findById(task.getId().getValue())
                    .orElseThrow(() -> new RuntimeException("Task not found"));
            entity.setProjectId(task.getProjectId());
            entity.setTitle(task.getTitle());
            entity.setDescription(task.getDescription());
            entity.setDueDate(task.getDueDate());
            entity.setCompleted(task.isCompleted());
        } else {
            // Create new entity
            entity = new TaskJpaEntity(
                    task.getProjectId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getDueDate(),
                    task.isCompleted()
            );
        }
        TaskJpaEntity saved = jpaRepository.save(entity);
        return new Task(
                new TaskId(saved.getId()),
                saved.getProjectId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getDueDate(),
                saved.isCompleted()
        );
    }

    @Override
    public List<Task> findByProjectId(Long projectId) {
        return jpaRepository.findByProjectId(projectId).stream()
                .map(e -> new Task(
                        new TaskId(e.getId()),
                        e.getProjectId(),
                        e.getTitle(),
                        e.getDescription(),
                        e.getDueDate(),
                        e.isCompleted()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Task> findByIdAndProjectId(Long taskId, Long projectId) {
        return jpaRepository.findByIdAndProjectId(taskId, projectId)
                .map(e -> new Task(
                        new TaskId(e.getId()),
                        e.getProjectId(),
                        e.getTitle(),
                        e.getDescription(),
                        e.getDueDate(),
                        e.isCompleted()
                ));
    }

    @Override
    public void deleteByIdAndProjectId(Long taskId, Long projectId) {
        jpaRepository.deleteByIdAndProjectId(taskId, projectId);
    }
}


