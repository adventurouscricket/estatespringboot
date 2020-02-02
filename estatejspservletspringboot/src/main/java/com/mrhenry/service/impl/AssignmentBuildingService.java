package com.mrhenry.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrhenry.dto.AssignmentBuildingDTO;
import com.mrhenry.service.IAssignmentBuildingService;
import com.mrhenry.utils.HttpClientUtil;

public class AssignmentBuildingService implements IAssignmentBuildingService {
	
	final static Logger logger = Logger.getLogger(BuildingService.class);
	
	@Override
	public List<AssignmentBuildingDTO> findAllByBuildingId(String url) {
		String result = HttpClientUtil.httpGet(url);
		try {
			return Arrays.asList(new ObjectMapper().readValue(result, AssignmentBuildingDTO[].class));
		} catch (IOException e) {
			logger.info("READ VALUE: " +e.getMessage(),e);
		}
		return new ArrayList<>();
	}

	@Override
	public Integer countAllByBuildingId(String url) {
		String result = HttpClientUtil.httpGet(url);
		AssignmentBuildingDTO assignmentBuildingDTO = null;
		try {
			assignmentBuildingDTO = new ObjectMapper().readValue(result, AssignmentBuildingDTO.class);
			return assignmentBuildingDTO.getTotalItem();
		} catch (IOException e) {
			logger.info("COUNT ALL SERVICE: "+e.getMessage(),e);
		}
		return 0;
	}

}
