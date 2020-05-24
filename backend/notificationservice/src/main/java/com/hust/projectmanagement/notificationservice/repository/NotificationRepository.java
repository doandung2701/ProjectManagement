package com.hust.projectmanagement.notificationservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.projectmanagement.notificationservice.domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, String>{

	Page<Notification> findByUserIdOrderByCreatedDateDesc(Long userId,Pageable pageable);
	
	

}
