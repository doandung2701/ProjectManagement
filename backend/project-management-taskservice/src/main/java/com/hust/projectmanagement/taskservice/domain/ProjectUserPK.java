package com.hust.projectmanagement.taskservice.domain;

public class ProjectUserPK {
	private long projectId;
	private long userId;
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public ProjectUserPK(long projectId, long userId) {
		super();
		this.projectId = projectId;
		this.userId = userId;
	}
	public ProjectUserPK() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (projectId ^ (projectId >>> 32));
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
		ProjectUserPK other = (ProjectUserPK) obj;
		if (projectId != other.projectId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
	
}
