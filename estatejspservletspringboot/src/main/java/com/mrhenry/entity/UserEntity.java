package com.mrhenry.entity;

import com.mrhenry.annotation.Column;
import com.mrhenry.annotation.Entity;

@Entity(name = "user")
public class UserEntity extends BaseEntity{
	
	@Column(name="username")
	private String userName;
	
	@Column(name="fullname")
	private String fullName;
	
	@Column(name="password")
	private String password;
	
	@Column(name="status")
	private Short status;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
}
