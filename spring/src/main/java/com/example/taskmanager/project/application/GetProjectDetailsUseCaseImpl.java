package com.example.taskmanager.project.application;

import com.example.taskmanager.project.domain.Project;
import com.example.taskmanager.project.domain.ProjectRepository;
import com.example.taskmanager.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetProjectDetailsUseCaseImpl implements GetProjectDetailsUseCase {

    private final ProjectRepository projectRepository;

    public GetProjectDetailsUseCaseImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project getProjectDetails(Long userId, Long projectId) {
        return projectRepository.findByIdAndUserId(projectId, userId)
                .orElseThrow(() -> new NotFoundException("Project not found"));
    }
}

