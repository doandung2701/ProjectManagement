package com.hust.projectmanagement.taskservice.dto;

import java.io.Serializable;

import com.hust.projectmanagement.taskservice.domain.Status;

public class SummaryTaskAndStatus implements Serializable{
	private Status status;
	private long numOfTask;
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public long getNumOfTask() {
		return numOfTask;
	}
	public void setNumOfTask(long numOfTask) {
		this.numOfTask = numOfTask;
	}
	public SummaryTaskAndStatus(Status status, long numOfTask) {
		this.status = status;
		this.numOfTask = numOfTask;
	}
	
}	
