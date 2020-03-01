package com.hust.projectmanagement.projectservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hust.projectmanagement.projectservice.domain.Invite;

public interface InviteRepository extends JpaRepository<Invite, Long> {

	 Boolean existsByUserId(long userId);
	  
	  @Query("SELECT i.projectId FROM Invite i WHERE i.userId=:userId")
	  long getProjectIdByUserId(@Param("userId") long userId);
	  
	  @Query("SELECT i.projectId FROM Invite i WHERE i.userId=:userId")
	  List<Long> findByUserid(long userId);

	  List<Invite> findByProjectId(long projectId);

}
