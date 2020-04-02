package com.hust.projectmanagement.taskservice.request;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hust.projectmanagement.taskservice.domain.Category;
import com.hust.projectmanagement.taskservice.domain.Priority;
import com.hust.projectmanagement.taskservice.domain.Status;

public class CreateTaskRequest implements Serializable{
	@NotNull(message = "User created can not be null")
	private Long createdBy;
	@NotNull(message = "CreatedTime can not be null")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime createdTime;
	public LocalDateTime getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}
	@NotNull(message = "Deadline can not be null")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime deadline;
	@NotNull(message = "Description can not be null")
	@NotBlank(message = "Description can not be blank")
	private String description;
	@NotNull(message = "Name can not be null")
	@NotBlank(message = "Name can not be blank")
	private String name;
	@NotNull(message = "Project can not be null")
	private Long projectId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime startTime;
	private Status status;
	private Priority priority;
	private Category category;
	private List<CheckListRequest> checklists;
	private Long[] users;
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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
	public List<CheckListRequest> getChecklists() {
		return checklists;
	}
	public void setChecklists(List<CheckListRequest> checklists) {
		this.checklists = checklists;
	}
	public Long[] getUsers() {
		return users;
	}
	public void setUsers(Long[] users) {
		this.users = users;
	}
	
}
