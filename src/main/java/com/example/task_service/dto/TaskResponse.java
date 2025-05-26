package com.example.task_service.dto;

import java.time.LocalDateTime;

public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private String createdBy;
    private String assignedTo;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


