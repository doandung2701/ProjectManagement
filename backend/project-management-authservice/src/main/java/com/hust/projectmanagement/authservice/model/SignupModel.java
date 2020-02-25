package com.hust.projectmanagement.authservice.model;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignupModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank
	    @Size(min = 3, max = 50)
	    private String name;

	    @NotBlank
	    @Size(min = 3, max = 50)
	    private String username;

	    @NotBlank
	    @Size(max = 60)
	    @Email
	    private String email;

	    @NotBlank
	    @Size(min = 6, max = 40)
	    private String password;
	    private String user;
	    
		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	    
}
