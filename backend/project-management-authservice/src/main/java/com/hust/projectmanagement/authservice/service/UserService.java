package com.hust.projectmanagement.authservice.service;

import java.util.List;

import com.hust.projectmanagement.authservice.domain.User;
import com.hust.projectmanagement.authservice.resource.UserResource;

public interface UserService {
	List<UserResource> getAllUser();
	User getUser(long id);
	void create(User user);
	long getIdByUsername(String id);
	void save(User user);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}
