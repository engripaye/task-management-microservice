package com.engripaye.task_management_microservice.repository;

import com.engripaye.task_management_microservice.model.Task;
import com.engripaye.task_management_microservice.model.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class TaskRepositoryTest {
    @Container
    static PostgreSQLContainer<?> postgres= new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("taskdb")
            .withUsername("admin")
            .withPassword("secret");

    @Autowired
    private TaskRepository taskRepository;


    @Test
    void testSaveAndFindTask() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test task");
        task.setDescription("Test Description");
        task.setStatus(TaskStatus.OPEN);

        taskRepository.save(task);

        assertTrue(taskRepository.findById(1L).isPresent());
    }
}