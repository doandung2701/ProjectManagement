package com.hust.projectmanagement.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.projectmanagement.taskservice.domain.CheckList;

public interface CheckListRepository extends JpaRepository<CheckList, Long> {

}
