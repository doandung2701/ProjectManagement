package com.hust.projectmanagement.projectservice.service;

import static java.util.Collections.singletonList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hust.projectmanagement.projectservice.domain.Invite;
import com.hust.projectmanagement.projectservice.domain.Passcode;
import com.hust.projectmanagement.projectservice.domain.Project;
import com.hust.projectmanagement.projectservice.domain.User;
import com.hust.projectmanagement.projectservice.dto.InviteUserDto;
import com.hust.projectmanagement.projectservice.dto.NewProjectDto;
import com.hust.projectmanagement.projectservice.dto.ProjectDto;
import com.hust.projectmanagement.projectservice.dto.UpdateProjectDto;
import com.hust.projectmanagement.projectservice.exception.NotAuthorizeActionException;
import com.hust.projectmanagement.projectservice.exception.ProjectNotFoundException;
import com.hust.projectmanagement.projectservice.exception.UserNotFoundException;
import com.hust.projectmanagement.projectservice.repository.InviteRepository;
import com.hust.projectmanagement.projectservice.repository.PasscodeRepository;
import com.hust.projectmanagement.projectservice.repository.ProjectRepository;
import com.hust.projectmanagement.projectservice.repository.UserRepository;
import com.hust.projectmanagement.projectservice.resources.ProjectListResource;
import com.hust.projectmanagement.projectservice.resources.ProjectResource;
import com.hust.projectmanagement.projectservice.response.UserResponse;
import com.hust.projectmanagement.projectservice.utils.GenCodeUtils;

import common.domain.Notification;
import common.event.NotificationCreatedEvent;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.events.publisher.ResultWithEvents;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	InviteRepository inviteRepository;
	@Autowired
	PasscodeRepository passcodeRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	DomainEventPublisher domainEventPublisher;
	@Autowired
	private MailService mailService;

	@Override
	public boolean addUser(long uid, String code) {
		boolean isInvited = false;
		Optional<User> user = userRepository.findById(uid);
		if (!user.isPresent()) {
			return false;
		}
		Long projectId = passcodeRepository.getPidByCode(code);
		if (Objects.isNull(projectId)) {
			return false;
		}
		long pid = projectId.longValue();
		boolean IsInvited = inviteRepository.existsByUserIdAndProjectId(uid, pid);
		if (IsInvited) {
			isInvited = true;
			Project project = projectRepository.findById(pid).get();
			Set<User> users = project.getUsers();
			users.add(user.get());
			project.setUsers(users);
			projectRepository.save(project);
			ResultWithEvents<Project> projectWithEvents = Project.updateProject(project);
			domainEventPublisher.publish(common.domain.Project.class, project.getId(), projectWithEvents.events);
		} else {
			isInvited = false;
		}
		// TODO Auto-generated method stub
		return isInvited;
	}

	@Override
	public long createProject(NewProjectDto projectDto) {
		Project project = new Project();
		project.setName(projectDto.getName());
		project.setDescription(projectDto.getDescription());
		Optional<User> user = userRepository.findById(projectDto.getAdmin());
		if (!user.isPresent())
			return -1;
		project.setAdmin(projectDto.getAdmin());
		// TODO Auto-generated method stub
		Set<User> userList = new HashSet();
		userList.add(user.get());
		project.setUsers(userList);
		Project savedProject = projectRepository.save(project);
		String code = generatePasscodeForProject(savedProject.getId());
		long[] users = projectDto.getUsers();
		inviteUsers(users, code, savedProject.getId());
		ResultWithEvents<Project> projectWithEvents = Project.createProject(savedProject);
		domainEventPublisher.publish(common.domain.Project.class, savedProject.getId(), projectWithEvents.events);
		return 0;
	}

	private String generatePasscodeForProject(long id) {
		String code = GenCodeUtils.OTP(8);
		Passcode passcode = new Passcode();
		passcode.setCode(code);
		passcode.setProjectId(id);
		passcodeRepository.save(passcode);
		return code;
	}
	private String updatePasscode(long passcodeId) {
		String code=GenCodeUtils.OTP(8);
		Passcode passcode=this.passcodeRepository.getOne(passcodeId);
		passcode.setCode(code);
		passcodeRepository.save(passcode);
		return code;
	}
	
	@Override
	@Async
	public void inviteUsers(long[] users, String code, long pid) {
		// TODO Auto-generated method stub
		for (long uid : users) {
			String email = userRepository.findById(uid).get().getEmail();
			Invite invites = new Invite();
			invites.setProjectId(pid);
			invites.setUserId(uid);
			inviteRepository.save(invites);
			mailService.sendPasscode(email, code, projectRepository.findById(pid).get());
			common.domain.Notification noti=new Notification();
			noti.setContent("You have a invitation to project with passcode. Please go to email to get passcode");
			noti.setTitle("Project invitation");
			noti.setURL("joinProject");
			noti.setType("project");
			noti.setUserId(uid);
			noti.setProjectId(pid);
			noti.setTaskId(null);
			NotificationCreatedEvent notificationCreatedEvent=new NotificationCreatedEvent();
			notificationCreatedEvent.setNotificaiton(noti);
			domainEventPublisher.publish(common.domain.Notification.class, UUID.randomUUID().toString(), singletonList(notificationCreatedEvent));
		}
	}
	
	@Override
	public ProjectListResource getAll() {
		// TODO Auto-generated method stub
		ProjectListResource list = new ProjectListResource();
		list.setprojectList((List<ProjectResource>) projectRepository.findAll().stream()
				.map(p -> new ProjectResource(p)).collect(Collectors.toList()));
		return null;
	}

	@Override
	public ProjectListResource getAllByAdmin(long id) {
		// TODO Auto-generated method stub
		ProjectListResource list = new ProjectListResource();
		list.setprojectList(projectRepository.findByAdmin(id).stream().map(p -> new ProjectResource(p))
				.collect(Collectors.toList()));
		return list;
	}

	@Override
	public void inviteUserToProjectByEmail(Long projectId, InviteUserDto inviteUserDto) {
		// TODO Auto-generated method stub
		boolean hasProject = projectRepository.existsById(projectId);
		if (!hasProject) {
			throw new ProjectNotFoundException("Project not found with id " + projectId);
		}
		for (String email : inviteUserDto.getEmails()) {
			Optional<User> user = userRepository.findByEmail(email);
			if (user.isPresent()) {
				boolean isInvite = inviteRepository.existsByUserIdAndProjectId(user.get().getId(), projectId);
				if (!isInvite) {
					Invite invite = new Invite();
					invite.setProjectId(projectId);
					invite.setUserId(user.get().getId());
					inviteRepository.save(invite);
				}
			}

		}
	}

	@Override
	@Transactional
	public Page<ProjectResource> getAllProjectUserJoined(Long id, int page, int size,String filterText) {
		// TODO Auto-generated method stub
		Optional<User> user = this.userRepository.findById(id);
		if (!user.isPresent()) {
			throw new UserNotFoundException("User not found with id " + id);
		}
		PageRequest pageData = PageRequest.of(page, size);
		int pageSize = pageData.getPageSize();
		int currentPage = pageData.getPageNumber();
		int startItem = currentPage * pageSize;
		List<ProjectResource> result;
		if(filterText==null||filterText.equals("")) {
			result = user.get().getProject().stream().map(p->new ProjectResource(p)).collect(Collectors.toList());

		}else {
			result = user.get().getProject().stream().filter(p->p.getName().toLowerCase().contains(filterText.toLowerCase())||p.getDescription().toLowerCase().contains(filterText.toLowerCase())).map(p->new ProjectResource(p)).collect(Collectors.toList());

		}
		List<ProjectResource> list;
		if (result.size() < startItem) {
			list = Collections.emptyList();
		} else {
			 int toIndex = Math.min(startItem + pageSize, result.size());
	            list = result.subList(startItem, toIndex);
		}
		// ProjectListResource projectListResource=new ProjectListResource();
		// List<Project> projectUserJoined=user.get().getProject();
//		projectListResource.setprojectList((List<ProjectResource>) projectUserJoined.stream()
//				.map(p -> new ProjectResource(p)).collect(Collectors.toList()));
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), result.size());

	}

	@Override
	@Transactional
	public List<UserResponse> getUserJoinProject(Long projectId) {
		// TODO Auto-generated method stub
		List<UserResponse> result=new ArrayList<UserResponse>();
		Optional<Project> project=this.projectRepository.findById(projectId);
		if(!project.isPresent()) {
			return result;
		}
		result=project.get().getUsers().stream().map(u->UserResponse.createFromUser(u)).collect(Collectors.toList());
		return result;
	}

	@Override
	public ProjectDto getProjectDetailById(Long projectId) {
		// TODO Auto-generated method stub
		ProjectDto result=new ProjectDto();
		Project findedProjed=this.projectRepository.getOne(projectId);
		result.setAdmin(findedProjed.getAdmin());
		result.setDescription(findedProjed.getDescription());
		result.setId(findedProjed.getId());
		result.setName(findedProjed.getName());
		List<User> users=new ArrayList<>();
		for (User user : findedProjed.getUsers()) {
			User u=new User();
			u.setId(user.getId());
			u.setEmail(user.getEmail());
			u.setName(user.getName());
			u.setUsername(user.getUsername());
			users.add(u);
		}
		result.setUsers(users);
		return result;
	}
	@Transactional
	@Override
	public ProjectDto updateProject(Long projectId, UpdateProjectDto updateProjectDto) {
		// TODO Auto-generated method stub
		Optional<Project> project=this.projectRepository.findById(projectId);
		if(!project.isPresent())
			throw new ProjectNotFoundException("Project not found with id "+projectId);
		Project updateProject=project.get();
		Set<User> usersWasJoined=updateProject.getUsers();
		updateProject.setName(updateProjectDto.getName());
		updateProject.setDescription(updateProjectDto.getDescription());
		Set<User> usersJoinProject=new HashSet<>();
		List<Long> userNeedInvite=new ArrayList<>();
		for (Long userId : updateProjectDto.getUsers()) {
			User foundedUser=this.userRepository.getOne(userId);
			if (foundedUser!=null) {
				if(usersWasJoined.contains(foundedUser)) {
					usersJoinProject.add(foundedUser);
				}else {
					userNeedInvite.add(userId);
				}
				
			}
		}
		usersJoinProject.add(this.userRepository.getOne(updateProject.getAdmin()));
		updateProject.setUsers(usersJoinProject);
		Project savedProject=this.projectRepository.save(updateProject);
		ProjectDto result=new ProjectDto();
		result.setAdmin(savedProject.getAdmin());
		result.setDescription(savedProject.getDescription());
		result.setId(savedProject.getId());
		result.setName(savedProject.getName());
		List<User> users=new ArrayList<>();
		for (User user : savedProject.getUsers()) {
			User u=new User();
			u.setId(user.getId());
			u.setEmail(user.getEmail());
			u.setName(user.getName());
			u.setUsername(user.getUsername());
			users.add(u);
		}
		result.setUsers(users);
		Passcode passcode=passcodeRepository.findByProjectId(updateProject.getId());
		String newPasscode=this.updatePasscode(passcode.getId());
		inviteUsers(userNeedInvite.stream().mapToLong(u->u).toArray(), newPasscode, updateProject.getId());
		//Publish event
		ResultWithEvents<Project> projectWithEvents = Project.updateProject(savedProject);
		domainEventPublisher.publish(common.domain.Project.class, savedProject.getId(), projectWithEvents.events);
	
		return result;
	}

	@Override
	public boolean removeProject(Long projectId, Long userId) {
		// TODO Auto-generated method stub
		Optional<Project> project=this.projectRepository.findById(projectId);
		if (!project.isPresent()) {
			throw new ProjectNotFoundException("Project not found with id "+projectId);
		}
		if(project.get().getAdmin()!=userId) {
			throw new NotAuthorizeActionException("You do not have permission to delete this project");
		}
		this.projectRepository.deleteById(projectId);
		ResultWithEvents<Project> projectWithEvents = Project.deleteProject(project.get());
		domainEventPublisher.publish(common.domain.Project.class, projectId, projectWithEvents.events);
		return true;
	}

}
