package com.hust.projectmanagement.projectservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.projectmanagement.projectservice.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
	List<Project> findByAdmin(long admin);
}
