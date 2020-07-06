package com.hust.projectmanagement.taskservice.repository;

import java.util.List;

import com.hust.projectmanagement.taskservice.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hust.projectmanagement.taskservice.domain.Status;
import com.hust.projectmanagement.taskservice.domain.Task;
import com.hust.projectmanagement.taskservice.dto.CountTaskByProjectViewModel;
import com.hust.projectmanagement.taskservice.dto.SummaryTaskAndCategory;
import com.hust.projectmanagement.taskservice.dto.SummaryTaskAndStatus;

public interface TaskRepository extends JpaRepository<Task, Long> {
	@Query("select t from Task t join t.users u join t.project p where p.id=:projectId and u.id=:userId")
	List<Task> findByUserAndProject(@Param("userId") Long uid,@Param("projectId") Long pid);
	@Query(value = "select new com.hust.projectmanagement.taskservice.dto.SummaryTaskAndStatus(t.status,count(t.id)) from Task t join t.users u join t.project p where u.id=:userId and u in (select a from p.users a) group by t.status")
	List<SummaryTaskAndStatus> coutTaskByStatus(Long userId);
	@Query(value = "select new com.hust.projectmanagement.taskservice.dto.SummaryTaskAndCategory(t.category,count(t.id)) from Task t join t.users u join t.project p where u.id=:userId and  u in (select a from p.users a) group by t.category")
	List<SummaryTaskAndCategory> coutTaskByCategory(@Param("userId") Long userId);
	@Query("select t from Task t join t.project p where p.id=:projectId")
	List<Task> findByProjectId(@Param("projectId") Long projectId);
	@Query("select distinct t from Task t join t.project p join t.users u where p.id=:projectId and t.name LIKE CONCAT('%',:name,'%') and (u.name LIKE CONCAT('%',:user,'%') or u.username LIKE CONCAT('%',:user,'%') or u.email LIKE CONCAT('%',:user,'%')) and t.status=:status")
	Page<Task> searchCustomTaskByNameAndAssigneeAndStatus(@Param("projectId") Long projectId,@Param("name")String name, @Param("user")String user,@Param("status") Status status, Pageable pageable);
	@Query("select distinct t from Task t join t.project p join t.users u where  p.id=:projectId and t.name LIKE CONCAT('%',:name,'%') and (u.name LIKE CONCAT('%',:user,'%') or u.username LIKE CONCAT('%',:user,'%') or u.email LIKE CONCAT('%',:user,'%'))")
	Page<Task> searchCustomTaskByNameAndAssignee(@Param("projectId") Long projectId,@Param("name")String name, @Param("user")String user, Pageable pageable);
	@Query("select  t from Task t join t.users u where u.id=:userId")
	List<Task> findByUser(@Param("userId")Long uid);
	
	@Query("select new com.hust.projectmanagement.taskservice.dto.CountTaskByProjectViewModel(p.id,p.name,count(t.id)) from Task t join t.users u join t.project p where u.id=:userId and u in (select a from p.users a) group by p.id")
	List<CountTaskByProjectViewModel> countTaskByProjectIdOfUser(@Param("userId")Long uid);
	@Query("select t from Task t join t.users u join t.project p where u.id=:userId and u in (select a from p.users a) order by t.deadline DESC")
	List<Task> findTop5ByUsersContainingOrderByDeadlineDesc(@Param("userId")Long uid,Pageable pageable);
}
