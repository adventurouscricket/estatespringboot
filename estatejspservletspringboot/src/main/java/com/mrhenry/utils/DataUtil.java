package com.mrhenry.utils;

import java.util.HashMap;
import java.util.Map;

import com.mrhenry.enums.BuildingType;
import com.mrhenry.enums.District;

public class DataUtil {
	public static Map<String, String> getBuildingTypes () {
		Map<String, String> results = new HashMap<String, String>();
		for(BuildingType item: BuildingType.values()) {
			results.put(item.name(), item.getValue());
		}
		return results;
	}
	
	public static Map<String, String> getDistricts () {
		Map<String, String> results = new HashMap<String, String>();
		for(District item: District.values()) {
			results.put(item.name(), item.getValue());
		}
		return results;
	}
}
