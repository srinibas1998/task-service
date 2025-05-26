package com.example.task_service.service;

import com.example.task_service.dto.TaskRequest;
import com.example.task_service.entity.Task;
import com.example.task_service.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmailService emailService;  // inject email service instead of Kafka

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public void deleteTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " does not exist."));
        taskRepository.deleteById(id);

        // Send email instead of Kafka event
        String subject = "Task Deleted: " + task.getTaskName();
        String body = "Task " + task.getTaskName() + " has been deleted.";
        emailService.sendEmail(task.getEmail(), subject, body);
        String bodyformanager = "Task " + task.getTaskName() +"for your subordiante "+task.getTaskAssignTo()+" has been deleted.";
        emailService.sendEmail(task.getManagerEmail(),subject,body);
    }

    public Task createTask(TaskRequest request) {
        Task task = Task.builder()
                .taskName(request.getTaskName())
                .taskDescription(request.getTaskDescription())
                .taskAssignTo(request.getTaskAssignTo())
                .username(request.getUsername())
                .taskCreatedAt(request.getTaskCreatedAt())
                .taskUpdatedAt(request.getTaskUpdatedAt())
                .email(request.getEmail())
                .managerUsername(request.getManagerUsername())
                .managerEmail(request.getManagerEmail())
                .taskStatus(Task.TaskStatus.P)
                .build();

        Task savedTask = taskRepository.save(task);

        // Send email instead of Kafka event
        String subject = "New Task Created: " + savedTask.getTaskName();
        String body = "Task " + savedTask.getTaskName() + " has been created with status " + savedTask.getTaskStatus();

        emailService.sendEmail(savedTask.getEmail(), subject, body);
        String bodyformanager="Task " + savedTask.getTaskName() + " has been created for your subordinate"+savedTask.getTaskAssignTo()+" with status " + savedTask.getTaskStatus();
        emailService.sendEmail(savedTask.getManagerEmail(),subject,bodyformanager);

        return savedTask;
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task task = taskRepository.findById(id).map(t -> {
            t.setTaskName(updatedTask.getTaskName());
            t.setTaskDescription(updatedTask.getTaskDescription());
            t.setTaskAssignTo(updatedTask.getTaskAssignTo());
            t.setUsername(updatedTask.getUsername());
            t.setTaskStatus(updatedTask.getTaskStatus());
            t.setTaskUpdatedAt(LocalDateTime.now());
            t.setEmail(updatedTask.getEmail());
            t.setManagerEmail(updatedTask.getManagerEmail());
            t.setManagerUsername(updatedTask.getManagerUsername());
            return taskRepository.save(t);
        }).orElseThrow(() -> new RuntimeException("Task not found with id " + id));

        // Send email instead of Kafka event
        String subject = "Task Updated: " + task.getTaskName();
        String body = "Task " + task.getTaskName() + " has been updated. New status: " + task.getTaskStatus();
        emailService.sendEmail(task.getEmail(), subject, body);
        String bodymanger = "Task " + task.getTaskName() + " has been updated.for your subordinate"+task.getTaskAssignTo()+" New status: " + task.getTaskStatus();
        emailService.sendEmail(task.getManagerEmail(), subject, bodymanger);

        return task;
    }

    public List<Task> getTasksByUsername(String username) {
        return taskRepository.findByTaskAssignTo(username);
    }
}
