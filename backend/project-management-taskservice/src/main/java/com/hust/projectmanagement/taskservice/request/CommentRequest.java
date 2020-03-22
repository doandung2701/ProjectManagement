package com.hust.projectmanagement.taskservice.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class CommentRequest implements Serializable{
	private Long id;
	private Long userId;
	private String username;
	private Long taskId;
	@NotBlank
	private String content;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
