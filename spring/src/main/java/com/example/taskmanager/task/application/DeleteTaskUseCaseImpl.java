package com.example.taskmanager.task.application;

import com.example.taskmanager.task.domain.TaskRepository;
import com.example.taskmanager.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteTaskUseCaseImpl implements DeleteTaskUseCase {

    private final TaskRepository taskRepository;

    public DeleteTaskUseCaseImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public void deleteTask(Long projectId, Long taskId) {
        // Verify task exists
        taskRepository.findByIdAndProjectId(taskId, projectId)
                .orElseThrow(() -> new NotFoundException("Task not found"));

        taskRepository.deleteByIdAndProjectId(taskId, projectId);
    }
}

