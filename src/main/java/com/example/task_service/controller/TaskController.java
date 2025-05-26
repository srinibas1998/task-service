package com.example.task_service.controller;


import com.example.task_service.dto.TaskRequest;
import com.example.task_service.entity.Task;
import com.example.task_service.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/save")
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<String> createTask(@RequestBody TaskRequest request) {
        Task savedTask = taskService.createTask(request);
        return ResponseEntity.ok("Task created with ID: " + savedTask.getId());
    }
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.ok("Task deleted successfully.");
    }
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        Task task = taskService.updateTask(id, updatedTask);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Task>> getTasksByUser(@PathVariable String username) {
        List<Task> tasks = taskService.getTasksByUsername(username);
        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 if no tasks found
        }
        return ResponseEntity.ok(tasks);
    }
}

