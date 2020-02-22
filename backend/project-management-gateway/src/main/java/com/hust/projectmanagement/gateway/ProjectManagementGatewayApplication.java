package com.hust.projectmanagement.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import com.hust.projectmanagement.gateway.configuration.RibbonConfiguration;

@EnableZuulProxy
@EnableEurekaClient
@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
@SpringBootApplication
public class ProjectManagementGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagementGatewayApplication.class, args);
	}

}
