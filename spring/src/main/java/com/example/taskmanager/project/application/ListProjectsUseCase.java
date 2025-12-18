package com.example.taskmanager.project.application;

import com.example.taskmanager.project.domain.Project;

import java.util.List;

public interface ListProjectsUseCase {

    List<Project> listProjectsForUser(Long userId);
}


