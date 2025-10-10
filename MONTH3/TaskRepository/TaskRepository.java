package com.example.Task_Management.TaskRepository;

import com.example.Task_Management.module.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Long> {
}
