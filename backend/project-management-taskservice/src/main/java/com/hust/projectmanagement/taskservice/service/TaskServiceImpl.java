package com.hust.projectmanagement.taskservice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hust.projectmanagement.taskservice.domain.CheckList;
import com.hust.projectmanagement.taskservice.domain.Comment;
import com.hust.projectmanagement.taskservice.domain.Priority;
import com.hust.projectmanagement.taskservice.domain.Project;
import com.hust.projectmanagement.taskservice.domain.Status;
import com.hust.projectmanagement.taskservice.domain.Task;
import com.hust.projectmanagement.taskservice.domain.User;
import com.hust.projectmanagement.taskservice.dto.CheckListDto;
import com.hust.projectmanagement.taskservice.dto.DashboardDto;
import com.hust.projectmanagement.taskservice.exception.ResourceFoundException;
import com.hust.projectmanagement.taskservice.repository.CheckListRepository;
import com.hust.projectmanagement.taskservice.repository.CommentRepository;
import com.hust.projectmanagement.taskservice.repository.ProjectRepository;
import com.hust.projectmanagement.taskservice.repository.TaskRepository;
import com.hust.projectmanagement.taskservice.repository.UserRepository;
import com.hust.projectmanagement.taskservice.request.CommentRequest;
import com.hust.projectmanagement.taskservice.request.CreateTaskRequest;
import com.hust.projectmanagement.taskservice.request.UpdateCommonTaskRequest;
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
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private CheckListRepository checkListRepository;
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
		newTask.setCreatedTime(newTaskDto.getCreatedTime());
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
	public Task updateTask(UpdateCommonTaskRequest updateTaskDto) {
		// TODO Auto-generated method stub
		Optional<Task> task=this.taskRepository.findById(updateTaskDto.getTaskId());
		if(!task.isPresent()) {
			return null;
		}
		Task updateTask=task.get();
		updateTask.setName(updateTaskDto.getTaskName());
		updateTask.setDescription(updateTaskDto.getTaskDescription());
		updateTask.setDeadline(updateTaskDto.getDeadline());
		updateTask.setStartTime(updateTaskDto.getStartTime());
		updateTask.setStatus(updateTaskDto.getStatus());
		updateTask.setPriority(updateTaskDto.getPriority());
		updateTask.setCategory(updateTaskDto.getCategory());
		if(updateTaskDto.getUsers()!=null) {
			List<User> users=new ArrayList<>();
			for (Long userId : updateTaskDto.getUsers()) {
				Optional<User> findUser=this.userRepository.findById(userId);
				if(findUser.isPresent()) {
					users.add(findUser.get());
				}
			}
			updateTask.setUsers(users);
		}
		
		return this.taskRepository.save(updateTask);
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

	@Override
	public List<Comment> getCommentByTaskId(Long taskId) {
		// TODO Auto-generated method stub
		return this.commentRepository.findByTaskId(taskId);
	}

	@Override
	public Comment createComment(Long taskId, CommentRequest request) {
		// TODO Auto-generated method stub
		Comment comment=new Comment();
		comment.setContent(request.getContent());
		comment.setTaskId(taskId);
		comment.setUserId(request.getUserId());
		comment.setUsername(request.getUsername());
		comment=this.commentRepository.save(comment);
		return comment;
	}

	@Override
	public DashboardDto getCountTask(Long userId) {
		// TODO Auto-generated method stub
		DashboardDto dto=new DashboardDto();
		dto.setTaskByCategory(taskRepository.coutTaskByCategory(userId));
		dto.setTaskByStatus(taskRepository.coutTaskByStatus(userId));
		return dto;
	}

	@Override
	public Task addCheckList(Long taskId, @Valid CheckListDto dto) {
		// TODO Auto-generated method stub
		CheckList checkList=new CheckList();
		checkList.setDescription(dto.getDescription());
		checkList.setStatus(dto.getStatus());
		Task task=this.taskRepository.getOne(taskId);
		checkList.setTask(task);
		this.checkListRepository.save(checkList);
//		task.getChecklists().add(checkList);
		return this.taskRepository.save(task);
	}

	@Override
	public Task updateCheckList(Long taskId, @Valid CheckListDto dto) {
		// TODO Auto-generated method stub
		CheckList c=this.checkListRepository.findById(dto.getId()).get();
		c.setDescription(dto.getDescription());
		c.setStatus(dto.getStatus());
		this.checkListRepository.save(c);
		return this.taskRepository.getOne(taskId);
	}

	@Override
	public Task removeChecklist(Long taskId, Long checkListId) {
		// TODO Auto-generated method stub
		this.checkListRepository.deleteById(checkListId);
		
		return this.taskRepository.getOne(taskId);
	}

}
