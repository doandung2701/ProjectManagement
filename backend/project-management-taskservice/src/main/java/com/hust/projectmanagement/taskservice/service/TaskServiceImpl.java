package com.hust.projectmanagement.taskservice.service;

import static java.util.Collections.singletonList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
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
import com.hust.projectmanagement.taskservice.dto.CountTaskByProjectViewModel;
import com.hust.projectmanagement.taskservice.dto.DashboardDto;
import com.hust.projectmanagement.taskservice.dto.SearchTaskListModel;
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

import common.domain.Notification;
import common.event.NotificationCreatedEvent;
import io.eventuate.tram.events.publisher.DomainEventPublisher;

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
	@Autowired
	private DomainEventPublisher domainEventPublisher;
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
		sendNotification(newTask);
		return response;
	}
	@Async
	public void sendNotification(Task task) {
		for (User user : task.getUsers()) {
			common.domain.Notification noti=new Notification();
			noti.setContent("You have a new task into project "+task.getProject().getName());
			noti.setTitle("Task assign");
			noti.setURL("detail/"+task.getId());
			noti.setType("task");
			noti.setUserId(user.getId());
			noti.setProjectId(task.getProject().getId());
			noti.setTaskId(task.getId());
			NotificationCreatedEvent notificationCreatedEvent=new NotificationCreatedEvent();
			notificationCreatedEvent.setNotificaiton(noti);
			domainEventPublisher.publish(common.domain.Notification.class, UUID.randomUUID().toString(), singletonList(notificationCreatedEvent));
		}
		
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
	public Boolean removeTask(Long taskId,Long userId) {
		// TODO Auto-generated method stub
		User user=this.userRepository.getOne(userId);
		if (user==null) {
			throw new ResourceFoundException("User not found with id "+userId);
		}
		Task task = this.taskRepository.getOne(taskId);
		if (task==null) {
			throw new ResourceFoundException("Task not found with id "+taskId);
		}
		Long adminProject=task.getProject().getAdmin();
		Long userCreatedTask=task.getCreatedBy();
		if(userId==adminProject||userId==userCreatedTask) {
			this.taskRepository.delete(task);
			return true;
		}
		return false;
	}

	@Override
	public Page<TaskResponse> getProjectTasks(Long id, SearchTaskListModel model, int page, int size) {
		// TODO Auto-generated method stub
		
		PageRequest pageData = PageRequest.of(page, size);
		int pageSize = pageData.getPageSize();
		int currentPage = pageData.getPageNumber();
		int startItem = currentPage * pageSize;
		Page<Task> result;
		if(model.getStatus()==null) {
			result=this.taskRepository.searchCustomTaskByNameAndAssignee(id,model.getName(),model.getUser(),PageRequest.of(page,size));

		}else {
			result=this.taskRepository.searchCustomTaskByNameAndAssigneeAndStatus(id,model.getName(),model.getUser(),model.getStatus(),PageRequest.of(page,size));
		}
		return new PageImpl<>(result.getContent().stream().map(t->Task.createTaskResponseFromTask(t)).collect(Collectors.toList()),
				result.getPageable(), result.getSize());

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
		Task task=this.taskRepository.getOne(taskId);
		sendNotificationComment(task,comment);
		return comment;
	}
	@Async
	public void sendNotificationComment(Task task,Comment comment) {
		for (User user : task.getUsers()) {
			common.domain.Notification noti=new Notification();
			noti.setContent("Have new comment in task "+task.getName());
			noti.setTitle("Task comment");
			noti.setURL("detail/"+task.getId());
			noti.setType("comment");
			noti.setUserId(user.getId());
			noti.setProjectId(task.getProject().getId());
			noti.setTaskId(task.getId());
			NotificationCreatedEvent notificationCreatedEvent=new NotificationCreatedEvent();
			notificationCreatedEvent.setNotificaiton(noti);
			domainEventPublisher.publish(common.domain.Notification.class, UUID.randomUUID().toString(), singletonList(notificationCreatedEvent));
		}

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

	@Override
	public List<Task> getAllTaskOfUser(Long uid) {
		// TODO Auto-generated method stub
		return this.taskRepository.findByUser(uid);
	}

	@Override
	public List<CountTaskByProjectViewModel> countTaskByProjectIdOfUser(Long uid) {
		// TODO Auto-generated method stub
		return this.taskRepository.countTaskByProjectIdOfUser(uid);
	}

	@Override
	public List<TaskResponse> getTop5TaskOrderByDeadlineByUserId(Long userId) {
		// TODO Auto-generated method stub
		User user=this.userRepository.getOne(userId);
		List<Task> tasks=this.taskRepository.findTop5ByUsersContainingOrderByDeadlineDesc(user);

//		List<Task> tasks=this.taskRepository.findTop5
		return tasks.stream().map(task -> Task.createTaskResponseFromTask(task)).collect(Collectors.toList());
	}

}
