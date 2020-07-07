package com.hust.projectmanagement.projectservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hust.projectmanagement.projectservice.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
	List<Project> findByAdmin(long admin);
	
	@Query("select distinct p from Project p join p.users u where u.id=:userId and (p.name LIKE CONCAT('%',:name,'%') or p.description LIKE CONCAT('%',:description,'%'))")
	Page<Project> findByUserAndNameOrDescription(@Param("userId")Long uid, @Param("name") String name,@Param("description") String desctiption,Pageable pageable);
	@Query("select distinct p from Project p join p.users u where u.id=:userId")
	Page<Project> findByUserId(@Param("userId")Long uid,Pageable pageable);
}
