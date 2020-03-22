package com.hust.projectmanagement.taskservice.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.hust.projectmanagement.taskservice.domain.Category;
import com.hust.projectmanagement.taskservice.domain.Priority;
import com.hust.projectmanagement.taskservice.domain.Status;

public class TaskResponse implements Serializable{
	private Long id;
	private long createdBy;
	private LocalDateTime createdTime;
	private LocalDateTime deadline;
	private String description;
	private LocalDateTime modifiedTime;
	private String name;
	public ProjectRespone project;
	private LocalDateTime startTime;
	private Status status;
	private Priority priority;
	private Category category;
	private List<CheckListResponse> checklists;
	private List<UserResponse> users=new ArrayList<>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}
	public LocalDateTime getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}
	public LocalDateTime getDeadline() {
		return deadline;
	}
	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(LocalDateTime modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ProjectRespone getProject() {
		return project;
	}
	public void setProject(ProjectRespone project) {
		this.project = project;
	}
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public List<CheckListResponse> getChecklists() {
		return checklists;
	}
	public void setChecklists(List<CheckListResponse> checklists) {
		this.checklists = checklists;
	}
	public List<UserResponse> getUsers() {
		return users;
	}
	public void setUsers(List<UserResponse> users) {
		this.users = users;
	}
	
}
