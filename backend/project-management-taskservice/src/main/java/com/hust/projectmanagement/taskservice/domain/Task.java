package com.hust.projectmanagement.taskservice.domain;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "task")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private long createdBy;
		
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime createdTime;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime deadline;
	
	@Column(length = 65450, columnDefinition = "text")
	private String description;
	
	private long modifiedBy;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime modifiedTime;
	
	private String name;
	
	private long project;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime startTime;
	
	@Enumerated(EnumType.STRING)
    @Column(length = 60)
	private Status status;
	
	private String attachments;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 60)
	private Priority priority;
	
	@ManyToOne
	private Category category;
	
	@OneToMany
	@JoinColumn(name = "task_id")
	private Set<CheckList> checklists;
	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(long modifiedBy) {
		this.modifiedBy = modifiedBy;
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

	public long getProject() {
		return project;
	}

	public void setProject(long project) {
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

	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
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
}
