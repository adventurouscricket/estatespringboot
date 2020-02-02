package com.mrhenry.enums;

public enum BuildingType {
	TANG_TRET("Tầng trệt"),
	NGUYEN_CAN("Nguyên Căn"),
	NOI_THAT("Nội thất");
	
	private String value;
	
	BuildingType(String value) {
		this.value = value;	
	}
	
	public String getValue() {
		return value;
	}
}
