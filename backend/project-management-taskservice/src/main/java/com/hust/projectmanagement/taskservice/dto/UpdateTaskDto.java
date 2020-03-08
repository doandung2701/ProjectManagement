package com.hust.projectmanagement.taskservice.dto;

import com.hust.projectmanagement.taskservice.domain.Task;

public class UpdateTaskDto {
	private Task task;
	private long[] users;
	private long projectId;
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public long[] getUsers() {
		return users;
	}
	public void setUsers(long[] users) {
		this.users = users;
	}
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
}
