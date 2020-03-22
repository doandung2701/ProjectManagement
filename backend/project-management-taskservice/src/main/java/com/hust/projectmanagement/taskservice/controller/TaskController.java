package com.hust.projectmanagement.taskservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hust.projectmanagement.taskservice.domain.Comment;
import com.hust.projectmanagement.taskservice.domain.Task;
import com.hust.projectmanagement.taskservice.dto.APIResponse;
import com.hust.projectmanagement.taskservice.dto.APIStatus;
import com.hust.projectmanagement.taskservice.dto.UpdateTaskDto;
import com.hust.projectmanagement.taskservice.exception.ResourceFoundException;
import com.hust.projectmanagement.taskservice.request.CommentRequest;
import com.hust.projectmanagement.taskservice.request.CreateTaskRequest;
import com.hust.projectmanagement.taskservice.request.UpdateCommonTaskRequest;
import com.hust.projectmanagement.taskservice.response.APIPaginationResponse;
import com.hust.projectmanagement.taskservice.response.CalendarListResource;
import com.hust.projectmanagement.taskservice.response.CalendarResource;
import com.hust.projectmanagement.taskservice.response.TaskResponse;
import com.hust.projectmanagement.taskservice.service.TaskService;

@RestController
@RequestMapping("task")
public class TaskController {
	@Autowired
	private TaskService taskService;
	@PostMapping("createTask")
	public ResponseEntity<APIResponse>createTask(@RequestBody @Valid CreateTaskRequest task){
		TaskResponse taskResponse=this.taskService.createTask(task);
		
			return new ResponseEntity(new APIResponse(APIStatus.OK, taskResponse),HttpStatus.CREATED);
		
	}
	@GetMapping("getDetail/{taskId}")
	public ResponseEntity<?> getDetail(@PathVariable(name = "taskId",required = true) Long taskId){
			Task task= this.taskService.getDetailById(taskId);
			if(task==null)
				throw new ResourceFoundException("Task not found");
			return new ResponseEntity(Task.createTaskResponseFromTask(task),HttpStatus.OK);
	}
	@GetMapping("/getUserTasks/{userId}")
	public ResponseEntity<?> getUserTask(@PathVariable(name="userId",required = true) Long id,
			@RequestParam(value = "filter") String filterText,
			@RequestParam(value = "page",defaultValue = "0") int page, 
			  @RequestParam(value = "size",defaultValue = "5") int size) {
		Page<TaskResponse> taskLists=this.taskService.getUserTasks(id,filterText,page,size);
//		Page<ProjectResource> dto = projectService.getAllProjectUserJoined(id,page,size,filterText);
		APIPaginationResponse<TaskResponse> response=new APIPaginationResponse<>(taskLists);
		return new ResponseEntity(response, HttpStatus.OK);
	}
	@PutMapping("updateTask")
	public ResponseEntity<APIResponse>updateTask(@RequestBody @Valid UpdateCommonTaskRequest task){
		Task isCreated=this.taskService.updateTask(task);
		if(isCreated!=null)
			return new ResponseEntity(new APIResponse(APIStatus.OK, Task.createTaskResponseFromTask(isCreated)),HttpStatus.OK);
		else {
			throw new ResourceFoundException("Task not found");
		}
		
	}
	@DeleteMapping("deleteTask/{taskId}")
	public ResponseEntity<APIResponse>removeTask(@PathVariable(name = "taskId",required = true)Long taskId){
		boolean isRemoved=this.taskService.removeTask(taskId);
		if(isRemoved) {
			return new ResponseEntity<APIResponse>(new APIResponse<Object>(APIStatus.OK, "Delete task success"),HttpStatus.OK);
		}else {
			return new ResponseEntity<APIResponse>(new APIResponse<Object>(APIStatus.ERR_BAD_REQUEST,"Error when delete task"),HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/getTaskCalendar/{pid}/{uid}")
	  public ResponseEntity<CalendarListResource> getTaskCalendar(@PathVariable("pid") Long pid, @PathVariable("uid") Long uid) {
	    // service call
	    List<Task> tl =  this.taskService.getAllTaskOfUserAndProject(uid, pid);
	    List<CalendarResource> cl = new ArrayList<CalendarResource>();
	    for(Task t: tl) {
	      CalendarResource cr = new CalendarResource();
	      cr.setTitle(t.getName());
	      cr.setStart(t.getStartTime());
	      cr.setEnd(t.getDeadline());
	      cl.add(cr);
	    }
	    CalendarListResource clr = new CalendarListResource();
	    clr.setEvents(cl);
	    
	    return new ResponseEntity<CalendarListResource>(clr, HttpStatus.OK);
	  }
	@GetMapping("/getComment/{taskId}")
	public ResponseEntity<?> getComment(@PathVariable(name ="taskId",required = true) Long taskId){
		List<Comment> commentList=new ArrayList();
		try {
			commentList=this.taskService.getCommentByTaskId(taskId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return  new ResponseEntity(commentList, HttpStatus.OK);
	}
	@PostMapping("/addComment/{taskId}")
	public ResponseEntity<?> addComment(@PathVariable(name ="taskId",required = true) Long taskId,@RequestBody @Valid CommentRequest request){
		Comment comment=this.taskService.createComment(taskId,request);
		return new ResponseEntity(comment, HttpStatus.OK);
	}
}
