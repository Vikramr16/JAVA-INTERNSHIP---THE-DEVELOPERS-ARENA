package com.example.Task_Management.controller;

import com.example.Task_Management.TaskService.TaskService;
import com.example.Task_Management.module.Task;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@CrossOrigin(origins = "*")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks(){return taskService.getAllTasks();}
    @PostMapping
    public Task createTask(@RequestBody Task task){return taskService.addTask(task);}
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task){
        return taskService.updateTask(id,task);
    }
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id){taskService.deleteTask(id);}
}
