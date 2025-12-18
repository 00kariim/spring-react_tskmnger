package com.example.taskmanager.project.application;

import com.example.taskmanager.project.domain.ProjectProgress;

public interface GetProjectProgressUseCase {

    ProjectProgress getProgress(Long userId, Long projectId);
}


