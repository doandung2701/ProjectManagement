package com.hust.projectmanagement.projectservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hust.projectmanagement.projectservice.domain.Passcode;

public interface PasscodeRepository extends JpaRepository<Passcode, Long> {
	  @Query("SELECT p.projectId FROM Passcode p WHERE p.code=:code")
	  long getPidByCode(@Param("code") String code);
}
