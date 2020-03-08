package com.hust.projectmanagement.taskservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hust.projectmanagement.taskservice.domain.ProjectUserPK;
import com.hust.projectmanagement.taskservice.domain.Task;
import com.hust.projectmanagement.taskservice.domain.TaskUser;
import com.hust.projectmanagement.taskservice.domain.TaskUserId;
import com.hust.projectmanagement.taskservice.dto.NewTaskDto;
import com.hust.projectmanagement.taskservice.repository.ProjectUserRepository;
import com.hust.projectmanagement.taskservice.repository.TaskRepository;
import com.hust.projectmanagement.taskservice.repository.TaskUserRepository;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private ProjectUserRepository projectUserRepository;
	@Autowired
	private TaskUserRepository taskUserRepository;

	@Override
	public boolean createTask(NewTaskDto newTaskDto) {
		// TODO Auto-generated method stub
		long projectId = newTaskDto.getProjectId();
		long[] usersHanldeTask = newTaskDto.getUsers();
		boolean isUserInProject;
		int numOfUserAddedTask = 0;
		ProjectUserPK projectUserId = new ProjectUserPK();
		projectUserId.setProjectId(projectId);
		boolean isExistedProject = projectUserRepository.existsById(projectUserId);
		if (!isExistedProject) {
			return false;
		}
		Task savedTask = taskRepository.save(newTaskDto.getTask());
		for (long user : usersHanldeTask) {
			isUserInProject = projectUserRepository.existsById(new ProjectUserPK(projectId, user));
			if (isUserInProject) {
				numOfUserAddedTask++;
				taskUserRepository.save(new TaskUser(savedTask.getId(), user));
			}
		}
		if (numOfUserAddedTask == 0) {
			taskRepository.deleteById(savedTask.getId());
			return false;
		}
		return true;
	}

	@Override
	public boolean removeUserFromTask(long taskId, long userId) {
		// TODO Auto-generated method stub
		boolean isExistedUserInTask=taskUserRepository.existsById(new TaskUserId(taskId, userId));
		if (!isExistedUserInTask) {
			return false;
		}
		taskUserRepository.deleteById(new TaskUserId(taskId, userId));
		return true;
	}

	@Override
	public boolean addNewUserToTask(long taskId, long userId) {
		// TODO Auto-generated method stub
		boolean isExistedUserInTask=taskUserRepository.existsById(new TaskUserId(taskId, userId));
		if (isExistedUserInTask) {
			return false;
		}
		taskUserRepository.save(new TaskUser(taskId, userId));
		return true;
	}

}
