package com.hust.projectmanagement.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hust.projectmanagement.userservice.domain.User;
import com.hust.projectmanagement.userservice.dto.APIPaginationResponse;
import com.hust.projectmanagement.userservice.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	private UserService userService;
	@GetMapping("findByEmail/{emailUser}")
	public ResponseEntity<?> findUserByEmail(@PathVariable(name = "emailUser",required = true) String email,
			@RequestParam(value = "page",defaultValue = "0") int page, 
			  @RequestParam(value = "size",defaultValue = "5") int size
			){
		Page<User> searchedUser =userService.findByEmail(email,page,size);
		APIPaginationResponse<User> response=new APIPaginationResponse<>(searchedUser);
		return new ResponseEntity(response,HttpStatus.OK);
	}
}
