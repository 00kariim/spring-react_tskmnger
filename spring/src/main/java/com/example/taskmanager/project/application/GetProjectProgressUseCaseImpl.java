package com.example.taskmanager.project.application;

import com.example.taskmanager.project.domain.ProjectProgress;
import com.example.taskmanager.project.domain.ProjectRepository;
import com.example.taskmanager.shared.exception.NotFoundException;
import com.example.taskmanager.task.domain.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class GetProjectProgressUseCaseImpl implements GetProjectProgressUseCase {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public GetProjectProgressUseCaseImpl(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public ProjectProgress getProgress(Long userId, Long projectId) {
        // Verify project exists and belongs to user
        projectRepository.findByIdAndUserId(projectId, userId)
                .orElseThrow(() -> new NotFoundException("Project not found"));

        var tasks = taskRepository.findByProjectId(projectId);
        long totalTasks = tasks.size();
        long completedTasks = tasks.stream()
                .filter(com.example.taskmanager.task.domain.Task::isCompleted)
                .count();

        return new ProjectProgress(totalTasks, completedTasks);
    }
}

