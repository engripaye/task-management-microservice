package com.engripaye.task_management_microservice.repository;

import com.engripaye.task_management_microservice.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Long> {

}
