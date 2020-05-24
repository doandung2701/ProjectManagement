package com.hust.projectmanagement.notificationservice.controller;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.expression.spel.ast.OpPlus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hust.projectmanagement.notificationservice.controller.dto.APIPaginationResponse;
import com.hust.projectmanagement.notificationservice.controller.dto.NotificationDto;
import com.hust.projectmanagement.notificationservice.domain.Notification;
import com.hust.projectmanagement.notificationservice.service.NotificationService;


@RestController
@RequestMapping("notification")
public class NotificationController {
	@Autowired
	private NotificationService notificationService;

	@GetMapping
	public ResponseEntity<?> GetNotification(@RequestParam(name = "userId", required = true) Long userId,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "5") int size) {
		Page<NotificationDto> notificaions=this.notificationService.getNotificationByUserId(userId,page,size);
//		Page<ProjectResource> dto = projectService.getAllProjectUserJoined(id,page,size,filterText);
		APIPaginationResponse<NotificationDto> response=new APIPaginationResponse<>(notificaions);
		return new ResponseEntity(response, HttpStatus.OK);
	}
	@GetMapping("/{id}/read")
	public ResponseEntity<?> markRead(@PathVariable(name = "id",required = true)String id){
		Optional<Notification> notification=this.notificationService.findById(id);
		if(!notification.isPresent()) {
			return new ResponseEntity(notification,HttpStatus.BAD_REQUEST);
		}else {
			notification.get().setRead(true);
			notification.get().setReadDate(new Date());
			notification.get().setUpdatedDate(new Date());
			this.notificationService.update(notification.get());
			return new ResponseEntity(notification,HttpStatus.OK);
		}
	}

}
