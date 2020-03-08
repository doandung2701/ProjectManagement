package com.hust.projectmanagement.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.projectmanagement.taskservice.domain.TaskUser;
import com.hust.projectmanagement.taskservice.domain.TaskUserId;

public interface TaskUserRepository extends JpaRepository<TaskUser, TaskUserId> {

}
