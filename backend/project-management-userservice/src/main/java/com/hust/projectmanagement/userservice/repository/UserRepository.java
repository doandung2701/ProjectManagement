package com.hust.projectmanagement.userservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.projectmanagement.userservice.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Page<User> findByEmailContaining(String email,Pageable pageable);
}
