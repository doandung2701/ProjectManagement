package com.hust.projectmanagement.projectservice.service;

import org.springframework.data.domain.Page;

import com.hust.projectmanagement.projectservice.dto.InviteUserDto;
import com.hust.projectmanagement.projectservice.dto.NewProjectDto;
import com.hust.projectmanagement.projectservice.resources.ProjectListResource;
import com.hust.projectmanagement.projectservice.resources.ProjectResource;

public interface ProjectService {
	boolean addUser(long uid, String code);
	long createProject(NewProjectDto projectDto);
	void inviteUsers(long[] users, String code, long pid);
	ProjectListResource getAll();
	ProjectListResource getAllByAdmin(long id);
	void inviteUserToProjectByEmail(Long projectId, InviteUserDto inviteUserDto);
	Page<ProjectResource> getAllProjectUserJoined(Long id,int page,int size,String filterText);
}
