package com.hust.projectmanagement.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.projectmanagement.taskservice.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
