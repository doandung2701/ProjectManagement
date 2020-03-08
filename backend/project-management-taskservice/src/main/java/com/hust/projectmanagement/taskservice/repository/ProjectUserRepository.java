package com.hust.projectmanagement.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.projectmanagement.taskservice.domain.ProjectUser;
import com.hust.projectmanagement.taskservice.domain.ProjectUserPK;

public interface ProjectUserRepository extends JpaRepository<ProjectUser, ProjectUserPK>{

}
