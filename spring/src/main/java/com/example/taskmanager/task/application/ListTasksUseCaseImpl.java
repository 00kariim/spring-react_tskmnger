package com.example.taskmanager.task.application;

import com.example.taskmanager.task.domain.Task;
import com.example.taskmanager.task.domain.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListTasksUseCaseImpl implements ListTasksUseCase {

    private final TaskRepository taskRepository;

    public ListTasksUseCaseImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> listTasks(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }
}

