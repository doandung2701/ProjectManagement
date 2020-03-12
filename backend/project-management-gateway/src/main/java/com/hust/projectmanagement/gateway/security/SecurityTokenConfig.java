package com.hust.projectmanagement.gateway.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hust.projectmanagement.gateway.security.filter.JwtTokenAuthenticationFilter;

@EnableWebSecurity
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private JwtConfig jwtConfig;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.exceptionHandling().authenticationEntryPoint((request,response,e)->response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
		.and()
		.addFilterBefore(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
		.authorizeRequests()
		   .antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()  
		   .antMatchers("/project/**").hasRole("USER")
		   .antMatchers("/task/**").hasRole("USER")
		   .antMatchers("/user/**").hasRole("USER")
		   .anyRequest().authenticated(); 
	}
	@Bean
  	public JwtConfig jwtConfig() {
    	   return new JwtConfig();
  	}
}
