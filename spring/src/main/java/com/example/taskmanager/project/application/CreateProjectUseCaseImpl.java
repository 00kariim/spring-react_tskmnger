package com.example.taskmanager.project.application;

import com.example.taskmanager.project.domain.Project;
import com.example.taskmanager.project.domain.ProjectRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CreateProjectUseCaseImpl implements CreateProjectUseCase {

    private final ProjectRepository projectRepository;

    public CreateProjectUseCaseImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Long createProject(CreateProjectCommand command) {
        Project project = new Project(
                null, // Will be set by repository
                command.getOwnerId(),
                command.getTitle(),
                command.getDescription(),
                Instant.now()
        );
        Project saved = projectRepository.save(project);
        return saved.getId().getValue();
    }
}

