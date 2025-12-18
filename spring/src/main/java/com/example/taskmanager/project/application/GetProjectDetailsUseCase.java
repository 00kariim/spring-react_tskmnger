package com.example.taskmanager.project.application;

import com.example.taskmanager.project.domain.Project;

public interface GetProjectDetailsUseCase {

    Project getProjectDetails(Long userId, Long projectId);
}


