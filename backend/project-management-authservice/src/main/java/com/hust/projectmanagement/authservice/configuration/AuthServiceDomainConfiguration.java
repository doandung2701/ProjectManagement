package com.hust.projectmanagement.authservice.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.hust.projectmanagement.authservice.service.UserService;
import com.hust.projectmanagement.authservice.service.UserServiceImpl;

import io.eventuate.tram.events.publisher.TramEventsPublisherConfiguration;

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
@EntityScan
@Import({TramEventsPublisherConfiguration.class})
public class AuthServiceDomainConfiguration {

	 @Bean
	  public UserService userService() {
	    return new UserServiceImpl();
	  }
}
