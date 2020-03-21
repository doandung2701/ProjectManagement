package com.hust.projectmanagement.userservice.service;

import org.springframework.data.domain.Page;

import com.hust.projectmanagement.userservice.domain.User;

public interface UserService {
	Page<User> findByEmail(String email,int page,int size);
}
