package com.example.taskmanager.task.application;

import com.example.taskmanager.task.domain.Task;

import java.util.List;

public interface ListTasksUseCase {

    List<Task> listTasks(Long projectId);
}


