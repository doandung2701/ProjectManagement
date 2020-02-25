package com.hust.projectmanagement.authservice.service;

import java.util.Optional;

import com.hust.projectmanagement.authservice.domain.Role;
import com.hust.projectmanagement.authservice.domain.RoleName;

public interface RoleService {
	Optional<Role> findByName(RoleName rolename);
}
