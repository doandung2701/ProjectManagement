package com.hust.projectmanagement.projectservice.resources;

import java.util.List;

public class ProjectListResource {
	private List<ProjectResource> projectList;

	public List<ProjectResource> getprojectList() {
		return projectList;
	}

	public void setprojectList(List<ProjectResource> projectList) {
		this.projectList = projectList;
	}

}
