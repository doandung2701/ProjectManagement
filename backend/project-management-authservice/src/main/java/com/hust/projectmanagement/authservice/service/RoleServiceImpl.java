package com.hust.projectmanagement.authservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hust.projectmanagement.authservice.domain.Role;
import com.hust.projectmanagement.authservice.domain.RoleName;
import com.hust.projectmanagement.authservice.repository.RoleRepository;
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepository roleRepository;
	@Override
	public Optional<Role> findByName(RoleName rolename) {
		// TODO Auto-generated method stub
		return roleRepository.findByName(rolename);
	}

}
