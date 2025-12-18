package com.example.taskmanager.project.domain;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {

    Project save(Project project);

    List<Project> findByUserId(Long userId);

    Optional<Project> findByIdAndUserId(Long projectId, Long userId);
}


