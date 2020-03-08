package common.event;

import common.domain.Project;

public class ProjectCreatedEvent implements ProjectEvent {
	private Project project;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public ProjectCreatedEvent(Project project) {
		super();
		this.project = project;
	}

	public ProjectCreatedEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
