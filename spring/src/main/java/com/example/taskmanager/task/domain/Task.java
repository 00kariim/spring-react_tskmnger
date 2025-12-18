package com.example.taskmanager.task.domain;

import java.time.Instant;

public class Task {

    private final TaskId id;
    private final Long projectId;
    private final String title;
    private final String description;
    private final Instant dueDate;
    private final boolean completed;

    public Task(TaskId id,
                Long projectId,
                String title,
                String description,
                Instant dueDate,
                boolean completed) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    public TaskId getId() {
        return id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }
}


