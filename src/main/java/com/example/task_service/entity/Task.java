package com.example.task_service.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taskName;

    private String taskDescription;

    private String taskAssignTo;

    private String username;

    private LocalDateTime taskCreatedAt;

    private LocalDateTime taskUpdatedAt;
    private String email;
    private String managerUsername;
    private String managerEmail;


    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    public static enum TaskStatus {
        A, // Approved
        R, // Rejected
        P, // Pending
        C,  // Completed
        F // final approved
    }
}

