package com.hust.projectmanagement.taskservice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "projectsusers")
@IdClass(ProjectUserPK.class)
public class ProjectUser {
	@Id
	private long projectId;
	@Id
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
	public ProjectUser(long projectId, long userId) {
		super();
		this.projectId = projectId;
		this.userId = userId;
	}
	public ProjectUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
