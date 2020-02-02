package com.mrhenry.service;

import java.util.List;

import com.mrhenry.dto.AssignmentBuildingDTO;

public interface IAssignmentBuildingService {
	List<AssignmentBuildingDTO> findAllByBuildingId(String url);
	Integer countAllByBuildingId(String url);
}
