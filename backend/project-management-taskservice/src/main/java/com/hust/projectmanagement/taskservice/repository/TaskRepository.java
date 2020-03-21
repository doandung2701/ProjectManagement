package com.hust.projectmanagement.taskservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hust.projectmanagement.taskservice.domain.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
	@Query("select t from Task t join t.users u join t.project p where p.id=:projectId and u.id=:userId")
	List<Task> findByUserAndProject(@Param("userId") Long uid,@Param("projectId") Long pid);

}
