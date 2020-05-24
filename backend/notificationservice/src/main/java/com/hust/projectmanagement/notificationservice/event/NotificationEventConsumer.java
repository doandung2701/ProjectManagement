package com.hust.projectmanagement.notificationservice.event;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hust.projectmanagement.notificationservice.domain.Notification;
import com.hust.projectmanagement.notificationservice.repository.NotificationRepository;
import com.hust.projectmanagement.notificationservice.service.NotificationService;

import common.event.NotificationCreatedEvent;
import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;

public class NotificationEventConsumer {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private NotificationService notificationService;
	public DomainEventHandlers domainEventHandlers() {
		return DomainEventHandlersBuilder.forAggregateType("common.domain.Notification")
				.onEvent(NotificationCreatedEvent.class, this::handleNotificationCreatedEventHandler).build();
	}

	public void handleNotificationCreatedEventHandler(
			DomainEventEnvelope<NotificationCreatedEvent> domainEventEnvelope) {
		logger.debug("New message");
		common.domain.Notification envelopeData=domainEventEnvelope.getEvent().getNotificaiton();
		Notification notification=new Notification();
		notification.setContent(envelopeData.getContent());
		notification.setRead(false);
		notification.setTitle(envelopeData.getTitle());
		notification.setURL(envelopeData.getURL());
		notification.setType(envelopeData.getType());
		notification.setCreatedDate(new Date());
		notification.setId(domainEventEnvelope.getAggregateId());
		notification.setUserId(envelopeData.getUserId());
		notification.setProjectId(envelopeData.getProjectId());
		notification.setTaskId(envelopeData.getTaskId());
		notification.setType(envelopeData.getType());
		this.notificationRepository.save(notification);
		notificationService.send(notification);
	}
}
