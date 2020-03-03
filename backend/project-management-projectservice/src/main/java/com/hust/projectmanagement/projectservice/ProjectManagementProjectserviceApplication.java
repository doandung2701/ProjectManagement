package com.hust.projectmanagement.projectservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.hust.projectmanagement.projectservice.event.ProjectEventConsumer;

import io.eventuate.tram.consumer.common.TramNoopDuplicateMessageDetectorConfiguration;
import io.eventuate.tram.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import io.eventuate.tram.events.subscriber.TramEventSubscriberConfiguration;

@SpringBootApplication
@EnableEurekaClient
@Import({
	EventuateTramKafkaMessageConsumerConfiguration.class,
    TramNoopDuplicateMessageDetectorConfiguration.class,
    TramEventSubscriberConfiguration.class
})
public class ProjectManagementProjectserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagementProjectserviceApplication.class, args);
	}
	 @Bean
	  public ProjectEventConsumer userEventConsumer() {
	    return new ProjectEventConsumer();
	  }
	 @Bean
	  public DomainEventDispatcher userDomainEventDispatcher(ProjectEventConsumer projectEventConsumer, DomainEventDispatcherFactory domainEventDispatcherFactory) {
	    return domainEventDispatcherFactory.make("projectServiceEvents", projectEventConsumer.domainEventHandlers());
	  }
}
