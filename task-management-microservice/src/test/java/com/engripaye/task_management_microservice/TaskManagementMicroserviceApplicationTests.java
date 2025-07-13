package com.engripaye.task_management_microservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class TaskManagementMicroserviceApplicationTests {

	@Test
	void contextLoads() {
	}

}
