package com.hust.projectmanagement.taskservice.event;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hust.projectmanagement.taskservice.domain.ProjectUser;
import com.hust.projectmanagement.taskservice.domain.ProjectUserPK;
import com.hust.projectmanagement.taskservice.repository.ProjectUserRepository;

import common.domain.Project;
import common.domain.User;
import common.event.ProjectCreatedEvent;
import common.event.ProjectUpdatedEvent;
import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;

public class TaskEventConsumer {
	private Logger logger = LoggerFactory.getLogger(TaskEventConsumer.class);
	@Autowired
	private ProjectUserRepository projectUserRepository;

	public DomainEventHandlers domainEventHandlers() {
		return DomainEventHandlersBuilder.forAggregateType("common.domain.Project")
				.onEvent(ProjectCreatedEvent.class, this::handleProjectCreatedEventHandler)
				.onEvent(ProjectUpdatedEvent.class, this::handleProjectUpdatedEventHanlder).build();
	}

	public void handleProjectCreatedEventHandler(DomainEventEnvelope<ProjectCreatedEvent> domainEventEnvelope) {
		logger.debug("Handler project created event");
		Long projectId = Long.parseLong(domainEventEnvelope.getAggregateId());
		ProjectCreatedEvent projectCreatedEvent = domainEventEnvelope.getEvent();
		boolean isExisted=projectUserRepository.existsById(new ProjectUserPK(projectId, projectCreatedEvent.getProject().getAdmin()));
		if(!isExisted) {
			ProjectUser projectUser=new ProjectUser();
			projectUser.setProjectId(projectId);
			projectUser.setUserId(projectCreatedEvent.getProject().getAdmin());
			projectUserRepository.save(projectUser);
		}
	}
	public void handleProjectUpdatedEventHanlder(DomainEventEnvelope<ProjectUpdatedEvent> domainEventEnvelope) {
		logger.debug("Handler project updated event");
		Long projectId=Long.parseLong(domainEventEnvelope.getAggregateId());
		ProjectUpdatedEvent projectUpdatedEvent=domainEventEnvelope.getEvent();
		Project project=projectUpdatedEvent.getProject();
		Long admin=project.getAdmin();
		//boolean isExisted=projectUserRepository.existsById(new ProjectUserPK(projectId, admin));
		ProjectUserPK id=new ProjectUserPK();
		id.setProjectId(projectId);
		projectUserRepository.deleteById(id);
		List<User> userInProject=project.getUsers();
		List<ProjectUser> projectUsers=new ArrayList<ProjectUser>();
		for (User user : userInProject) {
			projectUsers.add(new ProjectUser(projectId,user.getId()));
		}
		projectUserRepository.saveAll(projectUsers);
	}
	
}
