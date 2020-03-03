package com.hust.projectmanagement.projectservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hust.projectmanagement.projectservice.domain.Project;
import com.hust.projectmanagement.projectservice.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	@Query("SELECT u.project FROM User u WHERE u.id=:id")
	List<Project> findProjectById(long id);

	Optional<User> findByEmail(String email);
}
