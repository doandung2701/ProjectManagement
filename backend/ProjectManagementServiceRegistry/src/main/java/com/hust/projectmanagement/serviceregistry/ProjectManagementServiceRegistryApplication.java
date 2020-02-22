package com.hust.projectmanagement.serviceregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ProjectManagementServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagementServiceRegistryApplication.class, args);
	}

}
