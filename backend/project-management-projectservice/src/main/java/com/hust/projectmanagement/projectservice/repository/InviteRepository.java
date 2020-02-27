package com.hust.projectmanagement.projectservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hust.projectmanagement.projectservice.domain.Invite;

public interface InviteRepository extends JpaRepository<Invite, Long> {

	 Boolean existsByUid(long uid);
	  
	  @Query("SELECT i.projectId FROM Invite i WHERE i.userId=:uid")
	  long getPidByUid(@Param("uid") long uid);
	  
	  @Query("SELECT i.projectId FROM Invite i WHERE i.userId=:uid")
	  List<Long> findByUid(long uid);

	  List<Invite> findByPid(long pid);

}
