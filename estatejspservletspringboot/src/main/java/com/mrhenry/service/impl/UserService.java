package com.mrhenry.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrhenry.dto.UserDTO;
import com.mrhenry.service.IUserService;
import com.mrhenry.utils.HttpClientUtil;

public class UserService implements IUserService {
	
	final static Logger logger = Logger.getLogger(BuildingService.class);
	
	@Override
	public List<UserDTO> findAllByBuildingId(String url) {
		String result = HttpClientUtil.httpGet(url);
		try {
			return Arrays.asList(new ObjectMapper().readValue(result, UserDTO[].class));
		} catch (IOException e) {
			logger.info("READ VALUE: " +e.getMessage(),e);
		}
		return new ArrayList<>();
	}

	@Override
	public Integer countAllExcludeAdmin(String url) {
		String result = HttpClientUtil.httpGet(url);
		UserDTO userDTO = null;
		try {
			userDTO = new ObjectMapper().readValue(result, UserDTO.class);
			return userDTO.getTotalItem();
		} catch (IOException e) {
			logger.info("COUNT ALL SERVICE: "+e.getMessage(),e);
		}
		return 0;
	}

}
