package com.hust.projectmanagement.projectservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hust.projectmanagement.projectservice.dto.APIPaginationResponse;
import com.hust.projectmanagement.projectservice.dto.CodeDTO;
import com.hust.projectmanagement.projectservice.dto.InviteUserDto;
import com.hust.projectmanagement.projectservice.dto.NewProjectDto;
import com.hust.projectmanagement.projectservice.dto.ProjectDto;
import com.hust.projectmanagement.projectservice.dto.UpdateProjectDto;
import com.hust.projectmanagement.projectservice.resources.ProjectListResource;
import com.hust.projectmanagement.projectservice.resources.ProjectResource;
import com.hust.projectmanagement.projectservice.response.UserResponse;
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
		if (result < 0)
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

	@GetMapping("/getProjectByAdmin/{userId}")
	public ResponseEntity<ProjectListResource> getAllProjectByAdmin(@PathVariable("userId") Long id) {
		ProjectListResource dto = projectService.getAllByAdmin(id);
		return new ResponseEntity<ProjectListResource>(dto, HttpStatus.OK);
	}
	@PostMapping("/joinProjectByCode")
	public ResponseEntity<Boolean> enterProject(@RequestBody CodeDTO code) {
		Boolean result=projectService.addUser(code.getUid(), code.getCode());
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	@PostMapping("/inviteUserToProject/{projectId}")
	public ResponseEntity<Boolean> inviteUserToProject(@PathVariable(name = "projectId",required = true) Long projectId,@RequestBody InviteUserDto inviteUserDto){
		 projectService.inviteUserToProjectByEmail(projectId,inviteUserDto);
		 return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	@GetMapping("/getProjectUserJoined/{userId}")
	public ResponseEntity<?> getAllProjectUserJoined(@PathVariable(name="userId",required = true) Long id,
			@RequestParam(value = "filter") String filterText,
			@RequestParam(value = "page",defaultValue = "0") int page, 
			  @RequestParam(value = "size",defaultValue = "5") int size) {
		Page<ProjectResource> dto = projectService.getAllProjectUserJoined(id,page,size,filterText);
		APIPaginationResponse<ProjectResource> response=new APIPaginationResponse<>(dto);
		return new ResponseEntity(response, HttpStatus.OK);
	}
	@GetMapping("/{projectId}/getUser")
	public ResponseEntity<?> getAllUserJoinedProject(@PathVariable(name = "projectId",required = true) Long projectId){
		List<UserResponse> response=this.projectService.getUserJoinProject(projectId);
		return new ResponseEntity(response, HttpStatus.OK);
	}
	@GetMapping("/detail/{projectId}")
	public ResponseEntity<?> getProjectDetai(@PathVariable(name="projectId",required = true) Long projectId){
		return new ResponseEntity(this.projectService.getProjectDetailById(projectId),HttpStatus.OK);
	}
	@PostMapping("/updateProject/{projectId}")
	public ResponseEntity<?> updateProject(@PathVariable(name = "projectId",required = true)Long projectId,@RequestBody UpdateProjectDto updateProjectDto){
		ProjectDto projectUpdated=this.projectService.updateProject(projectId,updateProjectDto);
		return new ResponseEntity(projectUpdated,HttpStatus.OK);
	}
	@DeleteMapping("/removeProject/{projectId}/{userId}")
	public ResponseEntity<?> deleteProject(@PathVariable(name = "projectId",required = true)Long projectId,@PathVariable(name ="userId" ,required = true)Long userId){
		boolean  isRemoved=this.projectService.removeProject(projectId,userId);
		if(isRemoved) {
			return new ResponseEntity<>("Delete project success",HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Error when delete project",HttpStatus.BAD_REQUEST);
		}
	}
	
}
