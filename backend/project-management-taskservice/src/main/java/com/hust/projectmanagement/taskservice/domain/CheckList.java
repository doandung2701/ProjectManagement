package com.hust.projectmanagement.taskservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hust.projectmanagement.taskservice.dto.CheckListDto;
import com.hust.projectmanagement.taskservice.request.CheckListRequest;
import com.hust.projectmanagement.taskservice.response.CheckListResponse;

@Entity
@Table(name = "checklist")
public class CheckList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String description;
	private Boolean status;
	@ManyToOne
	private Task task;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public CheckList() {

	}

	public static CheckList createCheckList(CheckListDto dto) {
		CheckList checkList = new CheckList();
		checkList.setDescription(dto.getDescription());
		checkList.setStatus(dto.getStatus());
		return checkList;
	}

	public static CheckList createCheckListFromRequest(CheckListRequest c) {
		// TODO Auto-generated method stub
		CheckList checkList = new CheckList();
		checkList.setDescription(c.getDescription());
		if (c.getStatus() == null) {
			checkList.setStatus(null);
		} else
			checkList.setStatus(c.getStatus());
		return checkList;
	}

	public static CheckListResponse createCheckListResponse(CheckList c) {
		// TODO Auto-generated method stub
		CheckListResponse checkList = new CheckListResponse();
		checkList.setDescription(c.getDescription());
		checkList.setStatus(c.getStatus());
		checkList.setId(c.getId());
		return checkList;
	}

}
