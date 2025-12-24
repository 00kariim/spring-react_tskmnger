package com.example.taskmanager.task.application;

import com.example.taskmanager.task.domain.Task;
import com.example.taskmanager.task.domain.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchTasksUseCaseImpl implements SearchTasksUseCase {

    private final TaskRepository taskRepository;

    public SearchTasksUseCaseImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> execute(Long projectId, String query, Boolean completed) {
        return taskRepository.search(projectId, query, completed);
    }
}
