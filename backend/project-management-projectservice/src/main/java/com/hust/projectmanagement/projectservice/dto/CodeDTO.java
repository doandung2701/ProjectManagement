package com.hust.projectmanagement.projectservice.dto;

import java.io.Serializable;

public class CodeDTO implements Serializable{
	private String code;
	  private long uid;

	  public String getCode() {
	    return code;
	  }
	  public void setCode(String code) {
	    this.code = code;
	  }
	  public long getUid() {
	    return uid;
	  }
	  public void setUid(long uid) {
	    this.uid = uid;
	  }
}
