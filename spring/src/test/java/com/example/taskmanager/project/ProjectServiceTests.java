package com.example.taskmanager.project;

import com.example.taskmanager.project.application.CreateProjectUseCase;
import com.example.taskmanager.project.application.CreateProjectUseCaseImpl;
import com.example.taskmanager.project.domain.Project;
import com.example.taskmanager.project.domain.ProjectId;
import com.example.taskmanager.project.domain.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTests {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private CreateProjectUseCaseImpl createProjectUseCase;

    @Test
    void createProject_success_returnsId() {
        var ownerId = 1L;
        var title = "My Project";
        var description = "Desc";
        var cmd = new CreateProjectUseCase.CreateProjectCommand(ownerId, title, description);

        var saved = new Project(new ProjectId(2L), ownerId, title, description, Instant.now());
        when(projectRepository.save(org.mockito.ArgumentMatchers.any())).thenReturn(saved);

        Long id = createProjectUseCase.createProject(cmd);

        assertNotNull(id);
        assertEquals(2L, id);
    }
}
