package com.hust.projectmanagement.taskservice.dto;

import java.io.Serializable;

public class CountTaskByProjectViewModel implements Serializable{
	private long projectId;
	private String projectName;
	private long numOfTask;
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public long getNumOfTask() {
		return numOfTask;
	}
	public void setNumOfTask(long numOfTask) {
		this.numOfTask = numOfTask;
	}
	public CountTaskByProjectViewModel(long projectId, String projectName, long numOfTask) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.numOfTask = numOfTask;
	}
	
}
