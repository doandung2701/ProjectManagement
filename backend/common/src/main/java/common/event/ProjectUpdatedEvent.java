package common.event;

import common.domain.Project;

public class ProjectUpdatedEvent implements ProjectEvent {
	private Project project;

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
