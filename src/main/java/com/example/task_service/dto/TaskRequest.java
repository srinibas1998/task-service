package com.example.task_service.dto;


import com.example.task_service.entity.Task.TaskStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TaskRequest {
    private String taskName;
    private String taskDescription;
    private String taskAssignTo;
    private LocalDateTime taskCreatedAt;
    private LocalDateTime taskUpdatedAt;
    private String username;
    private TaskStatus taskStatus;
    private String email;
    private String managerUsername;
    private String managerEmail;
}
