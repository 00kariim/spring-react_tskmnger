package com.example.taskmanager.task.application;

import com.example.taskmanager.task.domain.Task;

import java.util.List;

public interface ListTasksUseCase {

    List<Task> listTasks(Long projectId);

    // New pageable variant
    org.springframework.data.domain.Page<com.example.taskmanager.task.domain.Task> listTasks(Long projectId, int page, int size);
}


