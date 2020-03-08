package com.hust.projectmanagement.taskservice.domain;

import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "taskusers")
@IdClass(TaskUserId.class)
public class TaskUser {
	private long taskId;
	private long userId;
	public long getTaskId() {
		return taskId;
	}
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public TaskUser(long taskId, long userId) {
		super();
		this.taskId = taskId;
		this.userId = userId;
	}
	public TaskUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
