package com.hust.projectmanagement.taskservice.dto;

public class UpdateTaskDto {
	private TaskDto task;
	private long[] users;
	private long projectId;
	public TaskDto getTask() {
		return task;
	}
	public void setTask(TaskDto task) {
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
