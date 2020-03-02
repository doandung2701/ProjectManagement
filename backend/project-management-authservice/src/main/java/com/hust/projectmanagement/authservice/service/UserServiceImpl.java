package com.hust.projectmanagement.authservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hust.projectmanagement.authservice.domain.User;
import com.hust.projectmanagement.authservice.repository.UserRepository;
import com.hust.projectmanagement.authservice.resource.UserResource;

import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.events.publisher.ResultWithEvents;
@Service
@Transactional
public class UserServiceImpl implements UserService{
	@Autowired
	UserRepository userRepository;
	@Autowired  
	private DomainEventPublisher domainEventPublisher;

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
		User createdUser=userRepository.save(user);
	    ResultWithEvents<User> userWithEvents = User.createUser(createdUser);
		domainEventPublisher.publish(common.domain.User.class, createdUser.getId(), userWithEvents.events);
	}
	
	@Override
	public long getIdByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.getIdByUsername(username);
	}

	@Override
	public void save(User user) {
		// TODO Auto-generated method stub
		userRepository.save(user);
		
		
	}

	@Override
	public boolean existsByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.existsByUsername(username);
	}

	@Override
	public boolean existsByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.existsByEmail(email);
	}

}
