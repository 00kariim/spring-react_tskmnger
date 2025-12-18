package com.example.taskmanager.project.domain;

import java.time.Instant;

public class Project {

    private final ProjectId id;
    private final Long ownerId;
    private final String title;
    private final String description;
    private final Instant createdAt;

    public Project(ProjectId id, Long ownerId, String title, String description, Instant createdAt) {
        this.id = id;
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    public ProjectId getId() {
        return id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}


