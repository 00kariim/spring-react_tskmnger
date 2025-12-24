package com.example.taskmanager.task.application;

import com.example.taskmanager.task.domain.Task;

import java.util.List;

public interface SearchTasksUseCase {
    List<Task> execute(Long projectId, String query, Boolean completed);
}
