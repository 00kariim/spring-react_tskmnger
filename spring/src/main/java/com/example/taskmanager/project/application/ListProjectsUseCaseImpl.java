package com.example.taskmanager.project.application;

import com.example.taskmanager.project.domain.Project;
import com.example.taskmanager.project.domain.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListProjectsUseCaseImpl implements ListProjectsUseCase {

    private final ProjectRepository projectRepository;

    public ListProjectsUseCaseImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> listProjectsForUser(Long userId) {
        return projectRepository.findByUserId(userId);
    }
}

