package com.hust.projectmanagement.taskservice.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.hust.projectmanagement.taskservice.response.CategoryResponse;

@Entity
@Table(name = "category")
public class Category {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	private long id;
	private String name;
	private String description;
	
	@OneToMany
	@JoinColumn(name = "category_id")
	private Set<Task> tasks;
	
	public Set<Task> getTasks() {
		return tasks;
	}
	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public static CategoryResponse createCategoryResponse(Category category) {
		// TODO Auto-generated method stub
		CategoryResponse response=new CategoryResponse();
		response.setDescription(category.getDescription());
		response.setId(category.getId());
		response.setName(category.getName());
		return response;
	}
	
}
