package com.hust.projectmanagement.taskservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.hust.projectmanagement.taskservice.configuration.RibbonConfiguration;
import com.hust.projectmanagement.taskservice.event.TaskEventConsumer;
import com.hust.projectmanagement.taskservice.event.UserEventConsumer;

import io.eventuate.tram.consumer.common.TramNoopDuplicateMessageDetectorConfiguration;
import io.eventuate.tram.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;
import io.eventuate.tram.events.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import io.eventuate.tram.events.subscriber.TramEventSubscriberConfiguration;
import io.eventuate.tram.jdbckafka.TramJdbcKafkaConfiguration;

@SpringBootApplication
@EnableEurekaClient
@Import({ EventuateTramKafkaMessageConsumerConfiguration.class,
		TramNoopDuplicateMessageDetectorConfiguration.class,
		TramJdbcKafkaConfiguration.class,
		TramEventsPublisherConfiguration.class,
		TramEventSubscriberConfiguration.class })
@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
@EnableDiscoveryClient
public class ProjectManagementTaskserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagementTaskserviceApplication.class, args);
	}

	@Bean
	public TaskEventConsumer taskEventConsumer() {
		return new TaskEventConsumer();
	}
	@Bean
	public UserEventConsumer userEventConsumer() {
		return new UserEventConsumer();
	}
	@Bean
	public DomainEventDispatcher taskDomainEventDispatcher(TaskEventConsumer taskEventConsumer,
			DomainEventDispatcherFactory domainEventDispatcherFactory) {
		return domainEventDispatcherFactory.make("taskServiceEvents", taskEventConsumer.domainEventHandlers());
	}
//	@Bean(name = "userEventConsumer")
//	public DomainEventDispatcher userDomainEventDispatcher(UserEventConsumer userEventConsumer,
//			DomainEventDispatcherFactory domainEventDispatcherFactory) {
//		return domainEventDispatcherFactory.make("projectServiceEvents", userEventConsumer.domainEventHandlers());
//	}
}
