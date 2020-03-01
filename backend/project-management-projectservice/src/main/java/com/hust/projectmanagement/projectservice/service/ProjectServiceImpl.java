package com.hust.projectmanagement.projectservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hust.projectmanagement.projectservice.domain.Invite;
import com.hust.projectmanagement.projectservice.domain.Passcode;
import com.hust.projectmanagement.projectservice.domain.Project;
import com.hust.projectmanagement.projectservice.domain.User;
import com.hust.projectmanagement.projectservice.dto.NewProjectDto;
import com.hust.projectmanagement.projectservice.repository.InviteRepository;
import com.hust.projectmanagement.projectservice.repository.PasscodeRepository;
import com.hust.projectmanagement.projectservice.repository.ProjectRepository;
import com.hust.projectmanagement.projectservice.repository.UserRepository;
import com.hust.projectmanagement.projectservice.resources.ProjectListResource;
import com.hust.projectmanagement.projectservice.resources.ProjectResource;
import com.hust.projectmanagement.projectservice.utils.GenCodeUtils;

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
		boolean ifIsInvited = inviteRepository.existsById(uid);
		List<Long> invitedPid = inviteRepository.findByUserid(uid);
		boolean isChecked = false;
		for (Long id : invitedPid) {
			if (id == pid) {
				isChecked = true;
				break;
			}
		}
		if (ifIsInvited && isChecked) {
			isInvited = true;
			Project project = projectRepository.findById(pid).get();
			List<User> users = project.getUsers();
			users.add(user.get());
			project.setUsers(users);
			projectRepository.save(project);
		} else {
			isInvited = false;
		}
		isInvited = false;
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
		List<User> userList = new ArrayList<User>();
		userList.add(user.get());
		project.setUsers(userList);
		Project savedProject = projectRepository.save(project);
		String code = generatePasscodeForProject(savedProject.getId());
		long[] users = projectDto.getUsers();
		inviteUsers(users, code, savedProject.getId());
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

	@Override
	public void inviteUsers(long[] users, String code, long pid) {
		// TODO Auto-generated method stub
		for (long uid : users) {
			Invite invites = new Invite();
			invites.setProjectId(pid);
			invites.setUserId(uid);
			inviteRepository.save(invites);
			// TODO:send message borker for mailService
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
		return null;
	}

}
