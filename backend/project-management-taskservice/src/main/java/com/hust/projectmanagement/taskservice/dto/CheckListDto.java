package com.hust.projectmanagement.taskservice.dto;

import java.io.Serializable;

import com.hust.projectmanagement.taskservice.domain.Status;

public class CheckListDto implements Serializable{
	private long id;
	private String description;
	private Boolean status;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
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
	
}
