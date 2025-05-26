package com.example.task_service.repository;


import com.example.task_service.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByTaskAssignTo(String taskAssignTo);
}
