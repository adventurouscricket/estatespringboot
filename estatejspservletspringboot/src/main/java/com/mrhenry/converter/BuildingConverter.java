package com.mrhenry.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;

import com.mrhenry.dto.BuildingDTO;
import com.mrhenry.entity.BuildingEntity;
import com.mrhenry.entity.RentAreaEntity;
import com.mrhenry.repository.IRentAreaRepository;
import com.mrhenry.repository.impl.RentAreaRepository;

public class BuildingConverter {
	

//	@Inject
	private IRentAreaRepository rentAreaRepository;
	
	public BuildingConverter () {
		if(rentAreaRepository == null) {
			rentAreaRepository = new RentAreaRepository();
		} 
	}
	
	public BuildingDTO convertToDTO(BuildingEntity entity) {
		ModelMapper modelMapper = new ModelMapper();
		BuildingDTO result = modelMapper.map(entity, BuildingDTO.class);
		
		Map<String, Object> properties = new HashMap<>();
		properties.put("buildingid", entity.getId());
		
		List<RentAreaEntity> rentAreas = rentAreaRepository.findAll(properties, null);
		List<String> lstRentArea = new ArrayList<String>();
		for(RentAreaEntity item: rentAreas) {
			lstRentArea.add(item.getValue());
		}
		if(lstRentArea.size() > 0) {
			result.setRentArea(StringUtils.join(lstRentArea, ","));
		}
		
		if(StringUtils.isNotBlank((entity.getType()))) {
			result.setBuildingTypes(entity.getType().split(","));
		}
		return result;
	}
	
	public BuildingEntity convertToEntity(BuildingDTO dto) {
		ModelMapper modelMapper = new ModelMapper();
		BuildingEntity result = modelMapper.map(dto, BuildingEntity.class);
		if(StringUtils.isNotBlank(dto.getNumberOfBasement())) {
			result.setNumberOfBasement(Integer.valueOf(dto.getNumberOfBasement()));
		}
		if(StringUtils.isNotBlank(dto.getBuildingArea())) {
			result.setBuildingArea(Integer.valueOf(dto.getBuildingArea()));
		}
		return result;
	}
}
