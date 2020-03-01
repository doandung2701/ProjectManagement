package com.hust.projectmanagement.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

import io.eventuate.tram.events.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.events.subscriber.TramEventSubscriberConfiguration;
import io.eventuate.tram.jdbckafka.TramJdbcKafkaConfiguration;

@SpringBootApplication
@EnableEurekaClient
@Import({ TramJdbcKafkaConfiguration.class, TramEventsPublisherConfiguration.class,
		TramEventSubscriberConfiguration.class })
public class ProjectManagementAuthserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagementAuthserviceApplication.class, args);
	}
}
