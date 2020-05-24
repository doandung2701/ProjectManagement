package com.hust.projectmanagement.projectservice.dto;

public class UpdateProjectDto {
	private String name;
	private String description;
	private Long[] users;
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
	public Long[] getUsers() {
		return users;
	}
	public void setUsers(Long[] users) {
		this.users = users;
	}
	
}
