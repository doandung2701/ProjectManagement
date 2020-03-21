package com.hust.projectmanagement.userservice.dto;

import java.util.List;

public class ListUserResource {
	 private List<UserResource> userList;

	public List<UserResource> getUserList() {
		return userList;
	}

	public void setUserList(List<UserResource> userList) {
		this.userList = userList;
	}
	 
}
