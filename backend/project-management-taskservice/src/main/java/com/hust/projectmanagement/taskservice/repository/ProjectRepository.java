package com.hust.projectmanagement.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.projectmanagement.taskservice.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{

}
