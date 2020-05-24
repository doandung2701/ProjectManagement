package common.event;

public class ProjectDeletedEvent implements ProjectEvent{
	private Long projectId;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public ProjectDeletedEvent(Long projectId) {
		super();
		this.projectId = projectId;
	}

	public ProjectDeletedEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
