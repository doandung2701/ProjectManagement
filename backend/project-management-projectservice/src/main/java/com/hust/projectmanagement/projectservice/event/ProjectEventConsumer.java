package com.hust.projectmanagement.projectservice.event;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hust.projectmanagement.projectservice.domain.User;
import com.hust.projectmanagement.projectservice.repository.UserRepository;

import common.event.UserCreatedEvent;
import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;

public class ProjectEventConsumer {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserRepository userRepository;

	public DomainEventHandlers domainEventHandlers() {
		return DomainEventHandlersBuilder
				.forAggregateType("common.domain.User")
				.onEvent(UserCreatedEvent.class, this::handleUserCreatedEventHandler).build();
	}
	public void handleUserCreatedEventHandler(DomainEventEnvelope<UserCreatedEvent> domainEventEnvelope) {
			Long userid=Long.parseLong(domainEventEnvelope.getAggregateId());
			UserCreatedEvent userCreatedEvent=domainEventEnvelope.getEvent();
			Optional<User> possibleUser=userRepository.findById(userid);
			if(!possibleUser.isPresent()) {
				User user=new User();
				user.setId(userid);
				user.setEmail(userCreatedEvent.getUser().getEmail());
				user.setName(userCreatedEvent.getUser().getName());
				user.setPassword(userCreatedEvent.getUser().getPassword());
				user.setUsername(userCreatedEvent.getUser().getUsername());
				userRepository.save(user);
			}else {
				return ;
			}
	}
}
