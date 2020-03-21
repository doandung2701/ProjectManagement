package com.hust.projectmanagement.taskservice.request;

import java.io.Serializable;

import com.hust.projectmanagement.taskservice.domain.Status;

public class CheckListRequest implements Serializable{
	private String description;
	private Status status;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
}
