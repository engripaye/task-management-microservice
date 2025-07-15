package com.engripaye.task_management_microservice.controller;

import com.engripaye.task_management_microservice.model.Task;
import com.engripaye.task_management_microservice.model.TaskStatus;
import com.engripaye.task_management_microservice.repository.TaskRepository;
import jakarta.persistence.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    // ✅ Create a new task
    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        if (task.getId() != null) {
            return ResponseEntity.badRequest().body("Task ID should be null when creating a new task.");
        }
        return ResponseEntity.ok(taskRepository.save(task));
    }

    // ✅ Get all tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // ✅ Update a task by ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setStatus(updatedTask.getStatus());
                    try {
                        return ResponseEntity.ok(taskRepository.save(task));
                    } catch (OptimisticLockException e) {
                        return ResponseEntity.status(409).body("Conflict: Task was updated by another transaction.");
                    }
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Get a task by ID (Read only)
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Delete a task by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // ✅ Get tasks by status
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getTasksByStatus(@PathVariable String status) {
        TaskStatus taskStatus;
        try {
            taskStatus = TaskStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status: " + status);
        }

        List<Task> filteredTasks = taskRepository.findAll().stream()
                .filter(task -> task.getStatus() == taskStatus)
                .toList();

        return ResponseEntity.ok(filteredTasks);
    }
}
