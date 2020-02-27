package com.hust.projectmanagement.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.projectmanagement.taskservice.domain.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
