package com.hust.projectmanagement.taskservice.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;

import com.hust.projectmanagement.taskservice.domain.Comment;
import com.hust.projectmanagement.taskservice.domain.Task;
import com.hust.projectmanagement.taskservice.dto.CheckListDto;
import com.hust.projectmanagement.taskservice.dto.DashboardDto;
import com.hust.projectmanagement.taskservice.dto.SearchTaskListModel;
import com.hust.projectmanagement.taskservice.request.CommentRequest;
import com.hust.projectmanagement.taskservice.request.CreateTaskRequest;
import com.hust.projectmanagement.taskservice.request.UpdateCommonTaskRequest;
import com.hust.projectmanagement.taskservice.response.TaskResponse;

public interface TaskService {
	TaskResponse createTask(CreateTaskRequest newTask);
	Task updateTask(UpdateCommonTaskRequest updateTaskDto);
	Boolean removeTask(Long taskId);
	Page<TaskResponse> getProjectTasks(Long id, SearchTaskListModel model, int page, int size);
	List<Task> getAllTaskOfUserAndProject(Long uid, Long pid);
	Task getDetailById(Long taskId);
	List<Comment> getCommentByTaskId(Long taskId);
	Comment createComment(Long taskId, CommentRequest request);
	DashboardDto getCountTask(Long userId);
	Task addCheckList(Long taskId, @Valid CheckListDto dto);
	Task updateCheckList(Long taskId, @Valid CheckListDto dto);
	Task removeChecklist(Long taskId, Long checkListId);
	List<Task> getAllTaskOfUser(Long uid);
}