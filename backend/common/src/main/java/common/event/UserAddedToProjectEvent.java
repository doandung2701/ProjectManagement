package common.event;

import common.domain.ProjectUser;

public class UserAddedToProjectEvent implements ProjectEvent{
	private ProjectUser projectUser;

	public ProjectUser getProjectUser() {
		return projectUser;
	}

	public void setProjectUser(ProjectUser projectUser) {
		this.projectUser = projectUser;
	}
	
}
