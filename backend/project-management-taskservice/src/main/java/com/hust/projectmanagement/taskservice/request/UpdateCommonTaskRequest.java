package com.hust.projectmanagement.taskservice.request;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hust.projectmanagement.taskservice.domain.Category;
import com.hust.projectmanagement.taskservice.domain.Priority;
import com.hust.projectmanagement.taskservice.domain.Status;

public class UpdateCommonTaskRequest implements Serializable{
	private Long taskId;
	private String taskName;
	private String taskDescription;
	@NotNull(message = "Deadline can not be null")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime deadline;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime startTime;
	private Status status;
	private Priority priority;
	private Category category;
	private Long[] users;
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	public LocalDateTime getDeadline() {
		return deadline;
	}
	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Long[] getUsers() {
		return users;
	}
	public void setUsers(Long[] users) {
		this.users = users;
	}
	
}
