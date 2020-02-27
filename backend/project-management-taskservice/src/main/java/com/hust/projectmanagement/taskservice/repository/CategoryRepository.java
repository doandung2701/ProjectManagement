package com.hust.projectmanagement.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.projectmanagement.taskservice.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
