package com.hust.projectmanagement.taskservice.request;

import java.io.Serializable;

import com.hust.projectmanagement.taskservice.domain.Status;

public class CheckListRequest implements Serializable{
	private Long Id;
	private String description;
	private Boolean status;
	private Long taskId;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	} 
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	
}
