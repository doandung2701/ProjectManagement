package com.hust.projectmanagement.projectservice.service;

import com.hust.projectmanagement.projectservice.dto.InviteUserDto;
import com.hust.projectmanagement.projectservice.dto.NewProjectDto;
import com.hust.projectmanagement.projectservice.resources.ProjectListResource;

public interface ProjectService {
	boolean addUser(long uid, String code);
	long createProject(NewProjectDto projectDto);
	void inviteUsers(long[] users, String code, long pid);
	ProjectListResource getAll();
	ProjectListResource getAllByAdmin(long id);
	void inviteUserToProjectByEmail(Long projectId, InviteUserDto inviteUserDto);
	ProjectListResource getAllProjectUserJoined(Long id);
}
