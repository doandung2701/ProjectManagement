package com.hust.projectmanagement.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.hust.projectmanagement.userservice.event.UserEventConsumer;

import io.eventuate.tram.consumer.common.TramNoopDuplicateMessageDetectorConfiguration;
import io.eventuate.tram.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;
import io.eventuate.tram.events.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import io.eventuate.tram.events.subscriber.TramEventSubscriberConfiguration;
import io.eventuate.tram.jdbckafka.TramJdbcKafkaConfiguration;

@SpringBootApplication
@EnableEurekaClient
@Import({
	EventuateTramKafkaMessageConsumerConfiguration.class,
    TramNoopDuplicateMessageDetectorConfiguration.class,
    TramJdbcKafkaConfiguration.class,
    TramEventSubscriberConfiguration.class
})
public class ProjectManagementUserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagementUserserviceApplication.class, args);
	}
	 @Bean
	  public UserEventConsumer userEventConsumer() {
	    return new UserEventConsumer();
	  }
	 @Bean
	  public DomainEventDispatcher userDomainEventDispatcher(UserEventConsumer userEventConsumer, DomainEventDispatcherFactory domainEventDispatcherFactory) {
	    return domainEventDispatcherFactory.make("userServiceEvents", userEventConsumer.domainEventHandlers());
	  }
}
