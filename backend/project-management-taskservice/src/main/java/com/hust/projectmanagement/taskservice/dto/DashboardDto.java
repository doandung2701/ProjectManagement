package com.hust.projectmanagement.taskservice.dto;

import java.io.Serializable;
import java.util.List;

public class DashboardDto implements Serializable{
	private List<SummaryTaskAndStatus> taskByStatus;
	private List<SummaryTaskAndCategory> taskByCategory;
	public List<SummaryTaskAndStatus> getTaskByStatus() {
		return taskByStatus;
	}
	public void setTaskByStatus(List<SummaryTaskAndStatus> taskByStatus) {
		this.taskByStatus = taskByStatus;
	}
	public List<SummaryTaskAndCategory> getTaskByCategory() {
		return taskByCategory;
	}
	public void setTaskByCategory(List<SummaryTaskAndCategory> taskByCategory) {
		this.taskByCategory = taskByCategory;
	}
	
}
