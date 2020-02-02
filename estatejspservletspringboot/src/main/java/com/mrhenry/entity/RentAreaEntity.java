package com.mrhenry.entity;

import com.mrhenry.annotation.Column;
import com.mrhenry.annotation.Entity;

@Entity(name="rentarea")
public class RentAreaEntity extends BaseEntity{
	
	@Column(name="buildingid")
	private Long buildingId;
	
	@Column(name="value")
	private String value;
	
	public Long getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
