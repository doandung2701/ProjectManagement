package com.hust.projectmanagement.taskservice.response;

public class PageResponse{
	int size;
	long totalElements;
	int totalPages;
	int number;
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public long getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public PageResponse(int size, int totalElements, int totalPages, int number) {
		super();
		this.size = size;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.number = number;
	}
	public PageResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}