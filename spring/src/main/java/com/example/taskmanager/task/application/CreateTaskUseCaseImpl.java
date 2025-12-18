package com.example.taskmanager.task.application;

import com.example.taskmanager.task.domain.Task;
import com.example.taskmanager.task.domain.TaskRepository;
import com.example.taskmanager.shared.exception.NotFoundException;
import com.example.taskmanager.project.infrastructure.ProjectJpaRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CreateTaskUseCaseImpl implements CreateTaskUseCase {

    private final TaskRepository taskRepository;
    private final ProjectJpaRepository projectJpaRepository;

    public CreateTaskUseCaseImpl(TaskRepository taskRepository, ProjectJpaRepository projectJpaRepository) {
        this.taskRepository = taskRepository;
        this.projectJpaRepository = projectJpaRepository;
    }

    @Override
    public Long createTask(CreateTaskCommand command) {
        Long projectId = command.getProjectId();
        // Verify project exists to avoid DB FK exception and return 404
        if (projectId == null || !projectJpaRepository.existsById(projectId)) {
            throw new NotFoundException("Project not found");
        }

        Task task = new Task(
                null, // Will be set by repository
                command.getProjectId(),
                command.getTitle(),
                command.getDescription(),
                command.getDueDate(),
                false // Not completed initially
        );
        Task saved = taskRepository.save(task);
        return saved.getId().getValue();
    }
}

