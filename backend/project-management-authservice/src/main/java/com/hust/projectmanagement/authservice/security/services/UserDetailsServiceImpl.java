package com.hust.projectmanagement.authservice.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hust.projectmanagement.authservice.domain.User;
import com.hust.projectmanagement.authservice.repository.UserRepository;

import com.hust.projectmanagement.authservice.resource.UserResource;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user=userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User Not Found with -> username or email : " + username));
		return UserPrinciple.build(user);
	}

}
