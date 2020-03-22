package com.hust.projectmanagement.taskservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.projectmanagement.taskservice.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByTaskId(Long taskId);
}
