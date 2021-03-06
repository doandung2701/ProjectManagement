package com.hust.projectmanagement.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Import;

import com.hust.projectmanagement.authservice.configuration.RibbonConfiguration;

import io.eventuate.tram.events.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.events.subscriber.TramEventSubscriberConfiguration;
import io.eventuate.tram.jdbckafka.TramJdbcKafkaConfiguration;

@SpringBootApplication
@EnableEurekaClient
@Import({ TramJdbcKafkaConfiguration.class, TramEventsPublisherConfiguration.class,
		TramEventSubscriberConfiguration.class })
@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
@EnableDiscoveryClient
public class ProjectManagementAuthserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagementAuthserviceApplication.class, args);
	}
//	@Bean
//	public CorsFilter corsFilter() {
//	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	    final CorsConfiguration config = new CorsConfiguration();
//	    config.setAllowCredentials(true);
//	    config.setAllowedOrigins(Collections.singletonList("*"));
//	    config.setAllowedHeaders(Collections.singletonList("*"));
//	    config.setAllowedMethods(Arrays.stream(HttpMethod.values()).map(HttpMethod::name).collect(Collectors.toList()));
//	    source.registerCorsConfiguration("/**", config);
//	    return new CorsFilter(source);
//	}
}
