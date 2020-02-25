package com.hust.projectmanagement.authservice.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hust.projectmanagement.authservice.domain.Role;
import com.hust.projectmanagement.authservice.domain.RoleName;
import com.hust.projectmanagement.authservice.domain.User;
import com.hust.projectmanagement.authservice.model.LoginModel;
import com.hust.projectmanagement.authservice.model.SignupModel;
import com.hust.projectmanagement.authservice.response.JwtResponse;
import com.hust.projectmanagement.authservice.security.jwt.JwtProvider;
import com.hust.projectmanagement.authservice.service.RoleService;
import com.hust.projectmanagement.authservice.service.UserService;

@RestController
@RequestMapping("auth")
public class AuthController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtProvider jwtProvider;
	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody @Valid LoginModel loginModel){
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginModel.getUsername(), loginModel.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		long id = userService.getIdByUsername(userDetails.getUsername());
		JwtResponse jwtRes = new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities(), id);
		return ResponseEntity.ok(jwtRes);
	}
	@PostMapping("signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupModel signUpRequest){
		if (userService.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
		}

		if (userService.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
		}

		User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
				passwordEncoder.encode(signUpRequest.getPassword()));

		Set<Role> roles = new HashSet<>();

		if( signUpRequest.getUser().equalsIgnoreCase("U")) {
		  Role userRole = roleService.findByName(RoleName.ROLE_USER)
	        .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
	        roles.add(userRole);
	    
		} else if( signUpRequest.getUser().equalsIgnoreCase("A")) {
		  Role adminRole = roleService.findByName(RoleName.ROLE_ADMIN)
          .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
      roles.add(adminRole);
		}

		user.setRoles(roles);
		userService.save(user);
		return new ResponseEntity<>(true, HttpStatus.OK);
	}
}
