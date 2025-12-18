package com.example.taskmanager.task.application;

import java.time.Instant;

public interface CreateTaskUseCase {

    Long createTask(CreateTaskUseCase.CreateTaskCommand command);

    class CreateTaskCommand {
        private final Long projectId;
        private final String title;
        private final String description;
        private final Instant dueDate;

        public CreateTaskCommand(Long projectId, String title, String description, Instant dueDate) {
            this.projectId = projectId;
            this.title = title;
            this.description = description;
            this.dueDate = dueDate;
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
    }
}


