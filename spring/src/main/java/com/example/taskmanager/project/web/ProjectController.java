package com.example.taskmanager.project.web;

import com.example.taskmanager.project.application.CreateProjectUseCase;
import com.example.taskmanager.project.application.GetProjectDetailsUseCase;
import com.example.taskmanager.project.application.GetProjectProgressUseCase;
import com.example.taskmanager.project.application.ListProjectsUseCase;
import com.example.taskmanager.project.domain.Project;
import com.example.taskmanager.project.domain.ProjectProgress;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final CreateProjectUseCase createProjectUseCase;
    private final ListProjectsUseCase listProjectsUseCase;
    private final GetProjectDetailsUseCase getProjectDetailsUseCase;
    private final GetProjectProgressUseCase getProjectProgressUseCase;

    public ProjectController(CreateProjectUseCase createProjectUseCase,
                             ListProjectsUseCase listProjectsUseCase,
                             GetProjectDetailsUseCase getProjectDetailsUseCase,
                             GetProjectProgressUseCase getProjectProgressUseCase) {
        this.createProjectUseCase = createProjectUseCase;
        this.listProjectsUseCase = listProjectsUseCase;
        this.getProjectDetailsUseCase = getProjectDetailsUseCase;
        this.getProjectProgressUseCase = getProjectProgressUseCase;
    }

    @PostMapping
    public ResponseEntity<Long> createProject(Authentication authentication,
                                              @Valid @RequestBody CreateProjectRequest request) {
        Long userId = (Long) authentication.getPrincipal();
        Long projectId = createProjectUseCase.createProject(
                new CreateProjectUseCase.CreateProjectCommand(
                        userId,
                        request.getTitle(),
                        request.getDescription()
                )
        );
        return ResponseEntity.ok(projectId);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> listProjects(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        List<Project> projects = listProjectsUseCase.listProjectsForUser(userId);
        List<ProjectResponse> responses = projects.stream()
                .map(p -> new ProjectResponse(
                        p.getId().getValue(),
                        p.getTitle(),
                        p.getDescription(),
                        p.getCreatedAt()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProjectDetails(Authentication authentication,
                                                             @PathVariable Long projectId) {
        Long userId = (Long) authentication.getPrincipal();
        Project project = getProjectDetailsUseCase.getProjectDetails(userId, projectId);
        ProjectResponse response = new ProjectResponse(
                project.getId().getValue(),
                project.getTitle(),
                project.getDescription(),
                project.getCreatedAt()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{projectId}/progress")
    public ResponseEntity<ProjectProgress> getProjectProgress(Authentication authentication,
                                                              @PathVariable Long projectId) {
        Long userId = (Long) authentication.getPrincipal();
        ProjectProgress progress = getProjectProgressUseCase.getProgress(userId, projectId);
        return ResponseEntity.ok(progress);
    }
}


