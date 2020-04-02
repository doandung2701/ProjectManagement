package com.hust.projectmanagement.taskservice.dto;

import com.hust.projectmanagement.taskservice.domain.Category;

public class SummaryTaskAndCategory {
	private Category category;
	private long numOfTask;
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public long getNumOfTask() {
		return numOfTask;
	}
	public void setNumOfTask(long numOfTask) {
		this.numOfTask = numOfTask;
	}
	public SummaryTaskAndCategory(Category category, long numOfTask) {
		this.category = category;
		this.numOfTask = numOfTask;
	}
	
}
