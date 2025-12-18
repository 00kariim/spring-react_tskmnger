package com.example.taskmanager.task.application;

import com.example.taskmanager.task.domain.Task;
import com.example.taskmanager.task.domain.TaskRepository;
import com.example.taskmanager.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CompleteTaskUseCaseImpl implements CompleteTaskUseCase {

    private final TaskRepository taskRepository;

    public CompleteTaskUseCaseImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void completeTask(Long projectId, Long taskId) {
        Task task = taskRepository.findByIdAndProjectId(taskId, projectId)
                .orElseThrow(() -> new NotFoundException("Task not found"));

        if (!task.isCompleted()) {
            Task completedTask = new Task(
                    task.getId(),
                    task.getProjectId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getDueDate(),
                    true // Mark as completed
            );
            taskRepository.save(completedTask);
        }
    }
}

