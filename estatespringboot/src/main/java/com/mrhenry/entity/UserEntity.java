package com.mrhenry.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user")
public class UserEntity extends BaseEntity{

	@Column(name="username")
	private String userName;

	@Column(name="password")
	private String password;

	@Column(name="fullname")
	private String fullName;

	@Column(name="status")
	private Short status;
	
	@ManyToMany
	@JoinTable(name="userrole",
			joinColumns = @JoinColumn(name="userid"),
			inverseJoinColumns = @JoinColumn(name="roleid"))
	private List<RoleEntity> roles = new ArrayList<>();

	@ManyToMany(mappedBy = "staffs")
	private List<BuildingEntity> buildings = new ArrayList<>();

	public List<BuildingEntity> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<BuildingEntity> buildings) {
		this.buildings = buildings;
	}

	public List<RoleEntity> getRoles() {
		return roles;
	}
	public void setRoles(List<RoleEntity> roles) {
		this.roles = roles;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
}
