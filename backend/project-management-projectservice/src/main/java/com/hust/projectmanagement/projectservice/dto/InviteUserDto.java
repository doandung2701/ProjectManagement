package com.hust.projectmanagement.projectservice.dto;

import java.io.Serializable;

public class InviteUserDto implements Serializable{
	private String[] emails;

	public String[] getEmails() {
		return emails;
	}

	public void setEmails(String[] emails) {
		this.emails = emails;
	}
	
}
