package com.hust.projectmanagement.taskservice.service;

import com.hust.projectmanagement.taskservice.dto.NewTaskDto;

public interface TaskService {
	boolean createTask(NewTaskDto newTaskDto);
	boolean removeUserFromTask(long taskId, long userId);
	boolean addNewUserToTask(long taskId,long userId);
}