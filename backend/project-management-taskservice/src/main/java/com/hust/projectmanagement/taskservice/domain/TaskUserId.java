package com.hust.projectmanagement.taskservice.domain;

public class TaskUserId {
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
	public TaskUserId(long taskId, long userId) {
		super();
		this.taskId = taskId;
		this.userId = userId;
	}
	public TaskUserId() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (taskId ^ (taskId >>> 32));
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskUserId other = (TaskUserId) obj;
		if (taskId != other.taskId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
	
}
