package com.hust.projectmanagement.projectservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfiguration  extends WebMvcConfigurationSupport {
    @Override
    public void addCorsMappings(final CorsRegistry registry) {
    	registry.addMapping("/**")
        .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");
    }

}