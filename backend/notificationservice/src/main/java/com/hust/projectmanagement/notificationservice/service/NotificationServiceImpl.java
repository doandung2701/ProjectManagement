package com.hust.projectmanagement.notificationservice.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hust.projectmanagement.notificationservice.controller.dto.NotificationDto;
import com.hust.projectmanagement.notificationservice.domain.Notification;
import com.hust.projectmanagement.notificationservice.repository.NotificationRepository;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private SimpMessagingTemplate template;
	@Autowired
	private ModelMapper modelMapper;

	@Async
	@Override
	public void send(Notification notification) {
		// TODO Auto-generated method stub
		this.template.convertAndSend("/topic/notification", notification);
		notification.setSend(true);
		notification.setSendDate(new Date());
		this.notificationRepository.save(notification);
	}

	@Override
	public Page<NotificationDto> getNotificationByUserId(Long userId, int page, int size) {
		// TODO Auto-generated method stub
		PageRequest pageReq = PageRequest.of(page, size);

		Page<Notification> notification = notificationRepository.findByUserIdOrderByCreatedDateDesc(userId, pageReq);
		List<NotificationDto> notifications= notification.stream().map(this::convertToDto).collect(Collectors.toList());
		return new PageImpl<>(notifications,
				notification.getPageable(), notification.getSize());
	}

	private NotificationDto convertToDto(Notification notification) {
		NotificationDto notificationDto = modelMapper.map(notification, NotificationDto.class);

		return notificationDto;
	}


	@Override
	public NotificationDto update(Notification notification) {
		// TODO Auto-generated method stub
		this.notificationRepository.save(notification);
		return modelMapper.map(notification, NotificationDto.class);
	}

	@Override
	public Optional<Notification> findById(String id) {
		// TODO Auto-generated method stub
		Optional<Notification> notification=this.notificationRepository.findById(id);
		return notification;
	}

}
