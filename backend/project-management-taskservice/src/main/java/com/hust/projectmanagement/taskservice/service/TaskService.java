package com.hust.projectmanagement.taskservice.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.hust.projectmanagement.taskservice.domain.Task;
import com.hust.projectmanagement.taskservice.dto.NewTaskDto;
import com.hust.projectmanagement.taskservice.dto.UpdateTaskDto;
import com.hust.projectmanagement.taskservice.request.CreateTaskRequest;
import com.hust.projectmanagement.taskservice.response.TaskResponse;

public interface TaskService {
	TaskResponse createTask(CreateTaskRequest newTask);
	Task updateTask(UpdateTaskDto updateTaskDto);
	Boolean removeTask(Long taskId);
	Page<TaskResponse> getUserTasks(Long id, String filterText, int page, int size);
	List<Task> getAllTaskOfUserAndProject(Long uid, Long pid);
	Task getDetailById(Long taskId);
}