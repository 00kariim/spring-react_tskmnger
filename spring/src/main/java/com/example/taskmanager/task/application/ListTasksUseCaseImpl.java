package com.example.taskmanager.task.application;

import com.example.taskmanager.task.domain.Task;
import com.example.taskmanager.task.domain.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        // default to first page, 100 items to preserve previous behavior
        Page<Task> page = listTasks(projectId, 0, 100);
        return page.getContent();
    }

    @Override
    public Page<Task> listTasks(Long projectId, int page, int size) {
        var pageable = PageRequest.of(page, size);
        Page<Task> pageResult = taskRepository.findByProjectId(projectId, pageable);
        return pageResult;
    }

}

