package com.hust.projectmanagement.notificationservice.configuration;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.hust.projectmanagement.notificationservice.exception.CustomAsyncExceptionHandler;
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer{
	 @Override
	    public Executor getAsyncExecutor() {
	        ThreadPoolTaskScheduler scheduler=new ThreadPoolTaskScheduler();
	        scheduler.setPoolSize(10);
	        scheduler.initialize();
	        return scheduler;
	    }
	 @Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		// TODO Auto-generated method stub
		 return new CustomAsyncExceptionHandler();
	}
}
