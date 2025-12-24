package com.example.taskmanager.task.web;

import com.example.taskmanager.task.application.CompleteTaskUseCase;
import com.example.taskmanager.task.application.CreateTaskUseCase;
import com.example.taskmanager.task.application.DeleteTaskUseCase;
import com.example.taskmanager.task.application.ListTasksUseCase;
import com.example.taskmanager.task.application.SearchTasksUseCase;
import com.example.taskmanager.task.domain.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final CompleteTaskUseCase completeTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;
    private final ListTasksUseCase listTasksUseCase;
    private final SearchTasksUseCase searchTasksUseCase;

    public TaskController(CreateTaskUseCase createTaskUseCase,
                          CompleteTaskUseCase completeTaskUseCase,
                          DeleteTaskUseCase deleteTaskUseCase,
                          ListTasksUseCase listTasksUseCase,
                          SearchTasksUseCase searchTasksUseCase) {
        this.createTaskUseCase = createTaskUseCase;
        this.completeTaskUseCase = completeTaskUseCase;
        this.deleteTaskUseCase = deleteTaskUseCase;
        this.listTasksUseCase = listTasksUseCase;
        this.searchTasksUseCase = searchTasksUseCase;
    }

    @PostMapping
    public ResponseEntity<Long> createTask(@PathVariable Long projectId,
                                           @Valid @RequestBody CreateTaskRequest request,
                                           Authentication authentication) {
        // userId available from authentication if needed later
        Long taskId = createTaskUseCase.createTask(
                new CreateTaskUseCase.CreateTaskCommand(
                        projectId,
                        request.getTitle(),
                        request.getDescription(),
                        request.getDueDate()
                )
        );
        return ResponseEntity.ok(taskId);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> listTasks(@PathVariable Long projectId,
                                                        Authentication authentication) {
        List<Task> tasks = listTasksUseCase.listTasks(projectId);
        List<TaskResponse> responses = tasks.stream()
                .map(t -> new TaskResponse(
                        t.getId().getValue(),
                        t.getProjectId(),
                        t.getTitle(),
                        t.getDescription(),
                        t.getDueDate(),
                        t.isCompleted()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/{taskId}/complete")
    public ResponseEntity<Void> completeTask(@PathVariable Long projectId,
                                             @PathVariable Long taskId,
                                             Authentication authentication) {
        completeTaskUseCase.completeTask(projectId, taskId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long projectId,
                                           @PathVariable Long taskId,
                                           Authentication authentication) {
        deleteTaskUseCase.deleteTask(projectId, taskId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<TaskResponse>> searchTasks(@PathVariable Long projectId,
                                                          @RequestParam(value = "q", required = false) String q,
                                                          @RequestParam(value = "completed", required = false) Boolean completed) {
        List<Task> tasks = searchTasksUseCase.execute(projectId, q, completed);
        List<TaskResponse> responses = tasks.stream()
                .map(t -> new TaskResponse(
                        t.getId().getValue(),
                        t.getProjectId(),
                        t.getTitle(),
                        t.getDescription(),
                        t.getDueDate(),
                        t.isCompleted()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}


