package com.engripaye.task_management_microservice;

import org.springframework.boot.SpringApplication;

public class TestTaskManagementMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.from(TaskManagementMicroserviceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
