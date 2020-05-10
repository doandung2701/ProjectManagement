package com.hust.projectmanagement.taskservice.dto;

import java.io.Serializable;
import com.hust.projectmanagement.taskservice.domain.Status;

public class SearchTaskListModel implements Serializable{
	private String name;
	private String user;
	private Status status;
	public String getName() {
		return name==null?"":name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUser() {
		return user==null?"":user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
}
