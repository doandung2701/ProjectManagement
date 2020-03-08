package com.hust.projectmanagement.taskservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.hust.projectmanagement.taskservice.event.TaskEventConsumer;

import io.eventuate.tram.consumer.common.TramNoopDuplicateMessageDetectorConfiguration;
import io.eventuate.tram.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import io.eventuate.tram.events.subscriber.TramEventSubscriberConfiguration;

@SpringBootApplication
@EnableEurekaClient
@Import({ EventuateTramKafkaMessageConsumerConfiguration.class, TramNoopDuplicateMessageDetectorConfiguration.class,
		TramEventSubscriberConfiguration.class })
public class ProjectManagementTaskserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagementTaskserviceApplication.class, args);
	}

	@Bean
	public TaskEventConsumer taskEventConsumer() {
		return new TaskEventConsumer();
	}

	@Bean
	public DomainEventDispatcher taskDomainEventDispatcher(TaskEventConsumer taskEventConsumer,
			DomainEventDispatcherFactory domainEventDispatcherFactory) {
		return domainEventDispatcherFactory.make("taskServiceEvents", taskEventConsumer.domainEventHandlers());
	}
}
