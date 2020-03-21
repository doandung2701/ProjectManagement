package com.hust.projectmanagement.taskservice.service;

import com.hust.projectmanagement.taskservice.domain.Comment;

public interface CommentService {
	Comment add(Comment comment);
	Comment update(Comment comment);
	Comment remove(Comment comment);
}
