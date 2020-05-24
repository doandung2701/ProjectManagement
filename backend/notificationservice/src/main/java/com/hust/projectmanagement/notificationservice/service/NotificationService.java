package com.hust.projectmanagement.notificationservice.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;

import com.hust.projectmanagement.notificationservice.controller.dto.NotificationDto;
import com.hust.projectmanagement.notificationservice.domain.Notification;

public interface NotificationService {
	void send(Notification notification);

	Page<NotificationDto> getNotificationByUserId(Long userId, int page, int size);

	NotificationDto update (Notification notification);

	Optional<Notification> findById(String id);
}
