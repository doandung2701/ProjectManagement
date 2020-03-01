package com.hust.projectmanagement.projectservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hust.projectmanagement.projectservice.dto.CodeDTO;
import com.hust.projectmanagement.projectservice.dto.NewProjectDto;
import com.hust.projectmanagement.projectservice.resources.ProjectListResource;
import com.hust.projectmanagement.projectservice.service.ProjectService;

@RestController
@RequestMapping("project")
public class ProjectController {
	@Autowired
	private ProjectService projectService;

	@PostMapping("/createProject")
	public ResponseEntity<Boolean> createProject(
			@RequestBody NewProjectDto newProject) {
		long result = projectService.createProject(newProject);
		if (result <= 0)
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

	@GetMapping("/getProjectByAdmin/{userId}")
	public ResponseEntity<ProjectListResource> getAllProjectByAdmin(@PathVariable("userId") Long id) {
		ProjectListResource dto = projectService.getAllByAdmin(id);
		return new ResponseEntity<ProjectListResource>(dto, HttpStatus.OK);
	}

	public ResponseEntity<Boolean> enterProject(@RequestBody CodeDTO code) {
		projectService.addUser(code.getUid(), code.getCode());
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
}
