package com.hust.projectmanagement.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hust.projectmanagement.userservice.domain.User;
import com.hust.projectmanagement.userservice.dto.ListUserResource;
import com.hust.projectmanagement.userservice.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	@Override
	public Page<User> findByEmail(String email,int page,int size) {
		// TODO Auto-generated method stub
		Page<User> seachedUser=userRepository.findByEmailContaining(email,PageRequest.of(page,size));
//		
		return seachedUser;
	}

}
