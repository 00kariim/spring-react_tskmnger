package com.example.taskmanager.project.application;

public interface CreateProjectUseCase {

    Long createProject(CreateProjectUseCase.CreateProjectCommand command);

    class CreateProjectCommand {
        private final Long ownerId;
        private final String title;
        private final String description;

        public CreateProjectCommand(Long ownerId, String title, String description) {
            this.ownerId = ownerId;
            this.title = title;
            this.description = description;
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
    }
}


