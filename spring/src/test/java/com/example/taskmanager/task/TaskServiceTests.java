package com.example.taskmanager.task;

import com.example.taskmanager.task.application.CreateTaskUseCaseImpl;
import com.example.taskmanager.task.application.ListTasksUseCaseImpl;
import com.example.taskmanager.task.domain.Task;
import com.example.taskmanager.task.domain.TaskId;
import com.example.taskmanager.task.domain.TaskRepository;
import com.example.taskmanager.project.infrastructure.ProjectJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTests {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectJpaRepository projectJpaRepository;

    @InjectMocks
    private CreateTaskUseCaseImpl createTaskUseCase;

    @InjectMocks
    private ListTasksUseCaseImpl listTasksUseCase;

    @Test
    void createTask_success_returnsId() {
        var projectId = 10L;
        var title = "Task 1";
        var desc = "Desc";

        when(projectJpaRepository.existsById(projectId)).thenReturn(true);

        var saved = new Task(new TaskId(5L), projectId, title, desc, Instant.now(), false);
        when(taskRepository.save(org.mockito.ArgumentMatchers.any())).thenReturn(saved);

        Long id = createTaskUseCase.createTask(new com.example.taskmanager.task.application.CreateTaskUseCase.CreateTaskCommand(projectId, title, desc, null));

        assertNotNull(id);
        assertEquals(5L, id);
    }

    @Test
    void listTasks_returnsTasks() {
        var projectId = 10L;
        var t1 = new Task(new TaskId(1L), projectId, "T1", "D", Instant.now(), false);
        when(taskRepository.findByProjectId(org.mockito.ArgumentMatchers.eq(projectId), org.mockito.ArgumentMatchers.any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(t1), PageRequest.of(0, 100), 1));

        var tasks = listTasksUseCase.listTasks(projectId);
        assertEquals(1, tasks.size());
        assertEquals("T1", tasks.get(0).getTitle());
    }
}
