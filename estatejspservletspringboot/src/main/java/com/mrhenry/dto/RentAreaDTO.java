package com.mrhenry.dto;

public class RentAreaDTO extends BaseDTO<RentAreaDTO>{
	private Long buildingId;
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
