package com.hust.projectmanagement.projectservice.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import common.domain.User;
import common.event.ProjectCreatedEvent;
import common.event.ProjectUpdatedEvent;
import io.eventuate.tram.events.publisher.ResultWithEvents;
import static java.util.Collections.singletonList;

import java.io.Serializable;

@Entity
@Table(name = "projects")
public class Project implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	@Column(length = 65450, columnDefinition = "text")
	private String description;
	private long admin;
	@ManyToMany
	@JsonManagedReference
	private List<com.hust.projectmanagement.projectservice.domain.User> users=new ArrayList<>();;

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

	public List<com.hust.projectmanagement.projectservice.domain.User> getUsers() {
		return users;
	}

	public void setUsers(List<com.hust.projectmanagement.projectservice.domain.User> users) {
		this.users = users;
	}

	public static ResultWithEvents<Project> createProject(Project savedProject) {
		// TODO Auto-generated method stub
		common.domain.Project commonProject=new common.domain.Project();
		commonProject.setAdmin(savedProject.getAdmin());
		commonProject.setDescription(savedProject.getDescription());
		commonProject.setId(savedProject.getId());
		commonProject.setName(savedProject.getName());
		for (com.hust.projectmanagement.projectservice.domain.User user : savedProject.getUsers()) {
			common.domain.User commonUser=new User();
			commonUser.setEmail(user.getEmail());
			commonUser.setId(user.getId());
			commonUser.setName(user.getName());
			commonUser.setPassword(user.getPassword());
			commonUser.setUsername(user.getUsername());
			commonProject.getUsers().add(commonUser);
		}
		ProjectCreatedEvent projectCreatedEvent=new ProjectCreatedEvent();
		projectCreatedEvent.setProject(commonProject);
		return new ResultWithEvents<Project>(savedProject, singletonList(projectCreatedEvent));
	}

	public static ResultWithEvents<Project> updateProject(Project project) {
		// TODO Auto-generated method stub
		common.domain.Project commonProject=new common.domain.Project();
		commonProject.setAdmin(project.getAdmin());
		commonProject.setDescription(project.getDescription());
		commonProject.setId(project.getId());
		commonProject.setName(project.getName());
		for (com.hust.projectmanagement.projectservice.domain.User user : project.getUsers()) {
			common.domain.User commonUser=new User();
			commonUser.setEmail(user.getEmail());
			commonUser.setId(user.getId());
			commonUser.setName(user.getName());
			commonUser.setPassword(user.getPassword());
			commonUser.setUsername(user.getUsername());
			commonProject.getUsers().add(commonUser);
		}
		ProjectUpdatedEvent projectUpdatedEvent=new ProjectUpdatedEvent();
		projectUpdatedEvent.setProject(commonProject);
		return new ResultWithEvents<Project>(project, singletonList(projectUpdatedEvent));
	}
	

}
