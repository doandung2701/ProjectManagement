package com.hust.projectmanagement.taskservice.response;

public class ProjectRespone {
	private long id;
	private String name;
	private String description;
	private long admin;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getAdmin() {
		return admin;
	}
	public void setAdmin(long admin) {
		this.admin = admin;
	}
	
	
}
