package com.hust.projectmanagement.taskservice.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hust.projectmanagement.taskservice.domain.User;
import com.hust.projectmanagement.taskservice.repository.ProjectRepository;

import common.domain.Project;
import common.event.ProjectCreatedEvent;
import common.event.ProjectUpdatedEvent;
import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;

public class TaskEventConsumer {
	private Logger logger = LoggerFactory.getLogger(TaskEventConsumer.class);
	@Autowired
	private ProjectRepository projectRepository;
	public DomainEventHandlers domainEventHandlers() {
		return DomainEventHandlersBuilder
				.forAggregateType("common.domain.Project")
				.onEvent(ProjectCreatedEvent.class, this::handleProjectCreatedEventHandler)
				.onEvent(ProjectUpdatedEvent.class, this::handleProjectUpdatedEventHanlder)
				.build();
	}

	public void handleProjectCreatedEventHandler(DomainEventEnvelope<ProjectCreatedEvent> domainEventEnvelope) {
		logger.debug("Handler project created event");
		Long projectId = Long.parseLong(domainEventEnvelope.getAggregateId());
		ProjectCreatedEvent projectCreatedEvent = domainEventEnvelope.getEvent();
		boolean isExisted=this.projectRepository.existsById(projectCreatedEvent.getProject().getId());
		if(!isExisted) {
			com.hust.projectmanagement.taskservice.domain.Project project=new com.hust.projectmanagement.taskservice.domain.Project();
			project.setId(projectCreatedEvent.getProject().getId());
			project.setAdmin(projectCreatedEvent.getProject().getAdmin());
			project.setDescription(projectCreatedEvent.getProject().getDescription());
			project.setName(projectCreatedEvent.getProject().getName());
			List<User> users=new ArrayList<>();
			for (common.domain.User user : projectCreatedEvent.getProject().getUsers()) {
				users.add(new User(user.getName(), user.getUsername(), user.getEmail(), user.getPassword()));
			}
			project.setUsers(users);
			this.projectRepository.save(project);
		}
	}
	public void handleProjectUpdatedEventHanlder(DomainEventEnvelope<ProjectUpdatedEvent> domainEventEnvelope) {
		logger.debug("Handler project updated event");
		Long projectId=Long.parseLong(domainEventEnvelope.getAggregateId());
		ProjectUpdatedEvent projectUpdatedEvent=domainEventEnvelope.getEvent();
		Project project=projectUpdatedEvent.getProject();
		Optional<com.hust.projectmanagement.taskservice.domain.Project> projectInDB=this.projectRepository.findById(projectId);
		if(projectInDB.isPresent()) {
			com.hust.projectmanagement.taskservice.domain.Project updatedProject=projectInDB.get();
			updatedProject.setId(project.getId());
			updatedProject.setAdmin(project.getAdmin());
			updatedProject.setDescription(project.getDescription());
			updatedProject.setName(project.getName());
			List<User> users=new ArrayList<>();
			for (common.domain.User user : project.getUsers()) {
				users.add(new User(user.getName(), user.getUsername(), user.getEmail(), user.getPassword()));
			}
			updatedProject.setUsers(users);
			this.projectRepository.save(updatedProject);
		}
		
		
	}
	
}
