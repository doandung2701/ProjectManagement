package com.hust.projectmanagement.taskservice.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;

import com.hust.projectmanagement.taskservice.domain.Comment;
import com.hust.projectmanagement.taskservice.domain.Task;
import com.hust.projectmanagement.taskservice.dto.UpdateTaskDto;
import com.hust.projectmanagement.taskservice.request.CommentRequest;
import com.hust.projectmanagement.taskservice.request.CreateTaskRequest;
import com.hust.projectmanagement.taskservice.request.UpdateCommonTaskRequest;
import com.hust.projectmanagement.taskservice.response.TaskResponse;

public interface TaskService {
	TaskResponse createTask(CreateTaskRequest newTask);
	Task updateTask(UpdateCommonTaskRequest updateTaskDto);
	Boolean removeTask(Long taskId);
	Page<TaskResponse> getUserTasks(Long id, String filterText, int page, int size);
	List<Task> getAllTaskOfUserAndProject(Long uid, Long pid);
	Task getDetailById(Long taskId);
	List<Comment> getCommentByTaskId(Long taskId);
	Comment createComment(Long taskId, CommentRequest request);
}