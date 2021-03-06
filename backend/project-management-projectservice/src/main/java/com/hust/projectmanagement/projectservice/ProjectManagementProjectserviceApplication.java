package com.hust.projectmanagement.projectservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.hust.projectmanagement.projectservice.configuration.RibbonConfiguration;
import com.hust.projectmanagement.projectservice.event.ProjectEventConsumer;

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
    TramJdbcKafkaConfiguration.class, TramEventsPublisherConfiguration.class,
    TramEventSubscriberConfiguration.class
})
@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
@EnableDiscoveryClient
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
