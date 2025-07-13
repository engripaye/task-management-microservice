package com.engripaye.task_management_microservice.controller;

import com.engripaye.task_management_microservice.model.Task;
import com.engripaye.task_management_microservice.model.TaskStatus;
import com.engripaye.task_management_microservice.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {


    @Autowired
    private TaskRepository taskRepository;

    // create a task
    @PostMapping
    public Task createTask(@RequestBody Task task){
        return taskRepository.save(task);
    }

    // get all task
    @GetMapping
    public List<Task> getAllTask(){
        return taskRepository.findAll();
    }

    // get a task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id, @RequestBody Task updatedTask){
        Optional<Task> existingTask = taskRepository.findById(id);
        if(existingTask.isPresent()) {
            Task task = existingTask.get();
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setStatus(updatedTask.getStatus());
            return ResponseEntity.ok(taskRepository.save(task));
        }

        return ResponseEntity.notFound().build();

    }

    // delete a task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        if(taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // get tasks by status
    @GetMapping("/status/{status}")
    public List<Task> getTaskByStatus(@PathVariable String status){
        TaskStatus taskStatus = switch (status.toUpperCase()) {
            case "OPEN" -> TaskStatus.OPEN;
            case "IN_PROGRESS" -> TaskStatus.IN_PROGRESS;
            case "COMPLETED" -> TaskStatus.COMPLETED;
            default -> throw new IllegalArgumentException("Invalid status: " + status);
        };
        return taskRepository.findAll().stream()
                .filter(task -> task.getStatus() == taskStatus)
                .toList();
    }
}

