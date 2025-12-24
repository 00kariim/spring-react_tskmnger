package com.example.taskmanager.project;

import com.example.taskmanager.project.application.GetProjectProgressUseCaseImpl;
import com.example.taskmanager.project.domain.Project;
import com.example.taskmanager.project.domain.ProjectId;
import com.example.taskmanager.project.domain.ProjectProgress;
import com.example.taskmanager.project.domain.ProjectRepository;
import com.example.taskmanager.task.domain.Task;
import com.example.taskmanager.task.domain.TaskId;
import com.example.taskmanager.task.domain.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectProgressTests {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private GetProjectProgressUseCaseImpl getProjectProgressUseCase;

    @Test
    void getProgress_calculatesCorrectly() {
        var userId = 1L;
        var projectId = 2L;
        var project = new Project(new ProjectId(projectId), userId, "Title", "Desc", Instant.now());
        when(projectRepository.findByIdAndUserId(projectId, userId)).thenReturn(Optional.of(project));

        var t1 = new Task(new TaskId(1L), projectId, "T1", "D", Instant.now(), false);
        var t2 = new Task(new TaskId(2L), projectId, "T2", "D", Instant.now(), true);
        when(taskRepository.findByProjectId(projectId)).thenReturn(List.of(t1, t2));

        ProjectProgress progress = getProjectProgressUseCase.getProgress(userId, projectId);

        assertEquals(2, progress.getTotalTasks());
        assertEquals(1, progress.getCompletedTasks());
        assertTrue(progress.getProgressPercentage() > 0);
    }
}
