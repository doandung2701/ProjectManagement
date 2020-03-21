package com.hust.projectmanagement.taskservice.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hust.projectmanagement.taskservice.response.ProjectRespone;
@Entity
@Table(name = "projects")
public class Project implements Serializable{
	@Id
	private long id;
	private String name;
	@Column(length = 65450, columnDefinition = "text")
	private String description;
	private long admin;
	@ManyToMany
	@JsonManagedReference
	private List<User> users=new ArrayList<>();;
	
	@OneToMany(mappedBy = "project")
	private List<Task> tasks=new ArrayList<>();
	
	
	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public static ProjectRespone createProjectResponse(Project project) {
		// TODO Auto-generated method stub
		ProjectRespone response=new ProjectRespone();
		response.setAdmin(project.getAdmin());
		response.setDescription(project.getDescription());
		response.setId(project.getId());
		response.setName(project.getName());
		return response;
	}
}
