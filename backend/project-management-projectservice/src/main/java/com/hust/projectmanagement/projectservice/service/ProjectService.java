package com.hust.projectmanagement.projectservice.service;

import com.hust.projectmanagement.projectservice.dto.NewProjectDto;
import com.hust.projectmanagement.projectservice.resources.ProjectListResource;

public interface ProjectService {
	boolean addUser(long uid, String code);
	long createProject(NewProjectDto projectDto, long admin);
	void inviteUsers(long[] users, String code, long pid);
	ProjectListResource getAll();
	ProjectListResource getAllByAdmin(long id);
}
