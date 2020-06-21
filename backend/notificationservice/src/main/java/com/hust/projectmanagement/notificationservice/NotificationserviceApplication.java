package com.hust.projectmanagement.notificationservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.hust.projectmanagement.notificationservice.configuration.RibbonConfiguration;
import com.hust.projectmanagement.notificationservice.event.NotificationEventConsumer;

import io.eventuate.tram.consumer.common.TramNoopDuplicateMessageDetectorConfiguration;
import io.eventuate.tram.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;
import io.eventuate.tram.events.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import io.eventuate.tram.events.subscriber.TramEventSubscriberConfiguration;
import io.eventuate.tram.jdbckafka.TramJdbcKafkaConfiguration;

@SpringBootApplication
@EnableEurekaClient
@Import({ EventuateTramKafkaMessageConsumerConfiguration.class, TramNoopDuplicateMessageDetectorConfiguration.class,
		TramJdbcKafkaConfiguration.class, TramEventsPublisherConfiguration.class,
		TramEventSubscriberConfiguration.class })
@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
@EnableDiscoveryClient
public class NotificationserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationserviceApplication.class, args);
	}
	 @Bean
	  public NotificationEventConsumer nofiticationEventConsumer() {
	    return new NotificationEventConsumer();
	  }
	 @Bean
	  public DomainEventDispatcher userDomainEventDispatcher(NotificationEventConsumer notificationEventConsumer, DomainEventDispatcherFactory domainEventDispatcherFactory) {
	    return domainEventDispatcherFactory.make("notificationServiceEvents", notificationEventConsumer.domainEventHandlers());
	  }
	 @Bean
	 public ModelMapper modelMapper() {
	     return new ModelMapper();
	 }
}
