package com.hust.projectmanagement.projectservice.response;

import java.io.Serializable;

import com.hust.projectmanagement.projectservice.domain.User;

public class UserResponse implements Serializable{
	private long id;
	 private String name;
	 private String username;
	 private String email;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public static UserResponse createFromUser(User user) {
		UserResponse u=new UserResponse();
		u.setEmail(user.getEmail());
		u.setId(user.getId());
		u.setName(user.getName());
		u.setUsername(user.getUsername());
		return u;
	}
}
