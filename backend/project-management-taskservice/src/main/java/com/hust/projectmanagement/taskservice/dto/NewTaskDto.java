package com.hust.projectmanagement.taskservice.dto;

import javax.validation.constraints.NotNull;

public class NewTaskDto {
	@NotNull
	private TaskDto task;
	@NotNull
	private Long[] users;
	@NotNull
	private Long projectId;
	public TaskDto getTask() {
		return task;
	}
	public void setTask(TaskDto task) {
		this.task = task;
	}
	public Long[] getUsers() {
		return users;
	}
	public void setUsers(Long[] users) {
		this.users = users;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	
}
