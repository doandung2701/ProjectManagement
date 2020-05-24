package com.hust.projectmanagement.notificationservice.controller.dto;

import java.util.List;

import org.springframework.data.domain.Page;


public class APIPaginationResponse<T> {
	private List<T> content;
	private PageResponse page;

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public PageResponse getPage() {
		return page;
	}

	public void setPage(PageResponse page) {
		this.page = page;
	}

	public APIPaginationResponse(List<T> content, PageResponse page) {
		super();
		this.content = content;
		this.page = page;
	}

	public APIPaginationResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public APIPaginationResponse(Page<T> page) {
		this.content = page.getContent();
		this.page = new PageResponse();
		this.page.number = page.getNumber();
		this.page.size = page.getSize();
		this.page.totalElements = page.getTotalElements();
		this.page.totalPages = page.getTotalPages();
	}
}