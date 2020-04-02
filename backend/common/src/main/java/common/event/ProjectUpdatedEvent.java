package common.event;

import java.util.List;

import common.domain.Project;
import common.domain.User;

public class ProjectUpdatedEvent implements ProjectEvent {
	private Project project;
	private List<User> users;
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public ProjectUpdatedEvent(Project project) {
		super();
		this.project = project;
	}

	public ProjectUpdatedEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
}
