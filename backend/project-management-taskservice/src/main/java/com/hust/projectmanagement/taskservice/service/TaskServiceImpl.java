package com.hust.projectmanagement.taskservice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hust.projectmanagement.taskservice.domain.CheckList;
import com.hust.projectmanagement.taskservice.domain.Priority;
import com.hust.projectmanagement.taskservice.domain.Project;
import com.hust.projectmanagement.taskservice.domain.Status;
import com.hust.projectmanagement.taskservice.domain.Task;
import com.hust.projectmanagement.taskservice.domain.User;
import com.hust.projectmanagement.taskservice.dto.UpdateTaskDto;
import com.hust.projectmanagement.taskservice.exception.ResourceFoundException;
import com.hust.projectmanagement.taskservice.repository.ProjectRepository;
import com.hust.projectmanagement.taskservice.repository.TaskRepository;
import com.hust.projectmanagement.taskservice.repository.UserRepository;
import com.hust.projectmanagement.taskservice.request.CreateTaskRequest;
import com.hust.projectmanagement.taskservice.response.TaskResponse;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public TaskResponse createTask(CreateTaskRequest newTaskDto) {
		// TODO Auto-generated method stub
		Optional<User> createdBy = this.userRepository.findById(newTaskDto.getCreatedBy());
		if (!createdBy.isPresent()) {
			throw new ResourceFoundException("User not found");
		}
		Optional<Project> project = this.projectRepository.findById(newTaskDto.getProjectId());
		if (!project.isPresent()) {
			throw new ResourceFoundException("Project not found ");
		}

		Task newTask = new Task();
		newTask.setCreatedBy(newTaskDto.getCreatedBy());
		newTask.setCreatedTime(LocalDateTime.now());
		newTask.setDeadline(newTaskDto.getDeadline());
		newTask.setDescription(newTaskDto.getDescription());
		newTask.setModifiedTime(null);
		newTask.setName(newTaskDto.getName());
		newTask.setProject(project.get());
		if (newTaskDto.getStartTime() == null) {
			newTaskDto.setStartTime(LocalDateTime.now());
		}
		newTask.setStartTime(newTaskDto.getStartTime());
		if (newTaskDto.getStatus() == null) {
			newTaskDto.setStatus(Status.NOT_START);
		}
		newTask.setStatus(newTaskDto.getStatus());
		if (newTaskDto.getPriority() == null) {
			newTaskDto.setPriority(Priority.NONE);
		}
		newTask.setPriority(newTaskDto.getPriority());
		newTask.setCategory(newTaskDto.getCategory());
		newTask.setChecklists(new HashSet<>(newTaskDto.getChecklists().stream()
				.map(cr -> CheckList.createCheckListFromRequest(cr)).collect(Collectors.toList())));

		if (newTaskDto.getUsers() != null) {
			List<User> users = new ArrayList<>();
			for (Long userId : newTaskDto.getUsers()) {
				Optional<User> userInDb = this.userRepository.findById(userId);
				if (userInDb.isPresent()) {
					users.add(userInDb.get());
				}
			}
			newTask.setUsers(users);
		}

		newTask = this.taskRepository.save(newTask);
		TaskResponse response = Task.createTaskResponseFromTask(newTask);
		return response;
	}

	@Override
	public Task updateTask(UpdateTaskDto updateTaskDto) {
		// TODO Auto-generated method stub
//		TaskDto task=updateTaskDto.getTask();
//		Optional<Task> exsitedTask=this.taskRepository.findById(task.getId());
//		Optional<Project> project=this.projectRepository.findById(task.getProject().getId());
//		if(!exsitedTask.isPresent()) {
//			return null;
//		}
//		if(!project.isPresent()) {
//			return null;
//		}
//		Task updateTask=exsitedTask.get();
//		updateTask.setCategory(task.getCategory());
//		updateTask.setChecklists(new HashSet<>(task.getChecklists()));
//		updateTask.setCreatedBy(task.getCreatedBy());
//		updateTask.setCreatedTime(task.getCreatedTime());
//		updateTask.setDeadline(task.getDeadline());
//		updateTask.setDescription(task.getDescription());
//		updateTask.setModifiedTime(LocalDateTime.now());
//		updateTask.setName(task.getName());
//		updateTask.setPriority(task.getPriority());
//		updateTask.setProject(project.get());
//		updateTask.setStartTime(task.getStartTime());
//		updateTask.setStatus(task.getStatus());
//		updateTask.setUsers(task.getUsers());
//		updateTask=this.taskRepository.save(updateTask);
		return null;
	}

	@Override
	public Boolean removeTask(Long taskId) {
		// TODO Auto-generated method stub
		Task task = this.taskRepository.getOne(taskId);
		if (task == null)
			return false;
		this.taskRepository.delete(task);
		return true;
	}

	@Override
	public Page<TaskResponse> getUserTasks(Long id, String filterText, int page, int size) {
		// TODO Auto-generated method stub
		Optional<User> user = this.userRepository.findById(id);
		if (!user.isPresent()) {
			throw new ResourceFoundException("User not found with id " + id);
		}
		PageRequest pageData = PageRequest.of(page, size);
		int pageSize = pageData.getPageSize();
		int currentPage = pageData.getPageNumber();
		int startItem = currentPage * pageSize;
		List<TaskResponse> result;
		if (filterText == null || filterText.equals("")) {
			result = user.get().getTask().stream().map(p -> Task.createTaskResponseFromTask(p))
					.collect(Collectors.toList());

		} else {
			result = user.get().getTask().stream()
					.filter(p -> p.getName().toLowerCase().contains(filterText.toLowerCase())
							|| p.getDescription().toLowerCase().contains(filterText.toLowerCase()))
					.map(p -> Task.createTaskResponseFromTask(p)).collect(Collectors.toList());

		}
		List<TaskResponse> list;
		if (result.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, result.size());
			list = result.subList(startItem, toIndex);
		}
		return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), result.size());

	}

	@Override
	public List<Task> getAllTaskOfUserAndProject(Long uid, Long pid) {
		// TODO Auto-generated method stub
		return this.taskRepository.findByUserAndProject(uid,pid);
	}

	@Override
	public Task getDetailById(Long taskId) {
		// TODO Auto-generated method stub
		Optional<Task> task=this.taskRepository.findById(taskId);
		if(task.isPresent()) {
			return task.get();
		}
		return null;
	}

}
