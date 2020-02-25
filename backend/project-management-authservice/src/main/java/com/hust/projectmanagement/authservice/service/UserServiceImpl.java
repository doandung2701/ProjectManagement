package com.hust.projectmanagement.authservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hust.projectmanagement.authservice.domain.User;
import com.hust.projectmanagement.authservice.repository.UserRepository;
import com.hust.projectmanagement.authservice.resource.UserResource;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	@Autowired
	UserRepository userRepository;

	@Override
	public List<UserResource> getAllUser() {
		// TODO Auto-generated method stub
		return userRepository.findAll().stream().map(user->new UserResource(user)).collect(Collectors.toList());
	}

	@Override
	public User getUser(long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).get();
	}

	@Override
	public void create(User user) {
		// TODO Auto-generated method stub
		userRepository.save(user);
	}

}
