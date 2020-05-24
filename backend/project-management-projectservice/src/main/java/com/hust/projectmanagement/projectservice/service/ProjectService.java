package com.hust.projectmanagement.projectservice.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.hust.projectmanagement.projectservice.dto.InviteUserDto;
import com.hust.projectmanagement.projectservice.dto.NewProjectDto;
import com.hust.projectmanagement.projectservice.dto.ProjectDto;
import com.hust.projectmanagement.projectservice.dto.UpdateProjectDto;
import com.hust.projectmanagement.projectservice.resources.ProjectListResource;
import com.hust.projectmanagement.projectservice.resources.ProjectResource;
import com.hust.projectmanagement.projectservice.response.UserResponse;

public interface ProjectService {
	boolean addUser(long uid, String code);
	long createProject(NewProjectDto projectDto);
	void inviteUsers(long[] users, String code, long pid);
	ProjectListResource getAll();
	ProjectListResource getAllByAdmin(long id);
	void inviteUserToProjectByEmail(Long projectId, InviteUserDto inviteUserDto);
	Page<ProjectResource> getAllProjectUserJoined(Long id,int page,int size,String filterText);
	List<UserResponse> getUserJoinProject(Long projectId);
	ProjectDto getProjectDetailById(Long projectId);
	ProjectDto updateProject(Long projectId, UpdateProjectDto updateProjectDto);
	boolean removeProject(Long projectId, Long userId);
}
