package com.hust.projectmanagement.taskservice.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hust.projectmanagement.taskservice.response.TaskResponse;

@Entity
@Table(name = "task")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private long createdBy;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime createdTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime deadline;

	@Column(length = 65450, columnDefinition = "text")
	private String description;


	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime modifiedTime;

	private String name;

	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	public Project project;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime startTime;

	@Enumerated(EnumType.STRING)
	@Column(length = 60)
	private Status status;

	@Enumerated(EnumType.STRING)
	@Column(length = 60)
	private Priority priority;

	@Enumerated(EnumType.STRING)
	@Column(length = 60)
	private Category category;

	@OneToMany
	@JoinColumn(name = "task_id")
	private Set<CheckList> checklists;

	@ManyToMany
	@JsonManagedReference
	private List<User> users=new ArrayList<>();
	
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
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

	public Set<CheckList> getChecklists() {
		return checklists;
	}

	public void setChecklists(Set<CheckList> checklists) {
		this.checklists = checklists;
	}

	public static TaskResponse createTaskResponseFromTask(Task newTask) {
		// TODO Auto-generated method stub
		TaskResponse taskResponse=new TaskResponse();
		taskResponse.setCategory(taskResponse.getCategory());
		taskResponse.setChecklists(newTask.getChecklists().stream()
				.map(cl->CheckList.createCheckListResponse(cl)).collect(Collectors.toList()));
		taskResponse.setCreatedBy(newTask.getCreatedBy());
		taskResponse.setCreatedTime(newTask.getCreatedTime());
		taskResponse.setDeadline(newTask.getDeadline());
		taskResponse.setDescription(newTask.getDescription());
		taskResponse.setId(newTask.getId());
		taskResponse.setModifiedTime(newTask.getModifiedTime());
		taskResponse.setName(newTask.getName());
		taskResponse.setPriority(newTask.getPriority());
		taskResponse.setProject(Project.createProjectResponse(newTask.getProject()));
		taskResponse.setStartTime(newTask.getStartTime());
		taskResponse.setStatus(newTask.getStatus());
		taskResponse.setUsers(newTask.getUsers().stream()
				.map(u->User.createUserResponse(u)).collect(Collectors.toList()));
		return taskResponse;
	}
}
