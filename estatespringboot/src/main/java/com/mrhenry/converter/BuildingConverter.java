package com.mrhenry.converter;

import com.mrhenry.dto.BuildingDTO;
import com.mrhenry.entity.BuildingEntity;
import com.mrhenry.entity.RentAreaEntity;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BuildingConverter {

	public BuildingDTO convertToDTO(BuildingEntity entity) {
		ModelMapper modelMapper = new ModelMapper();
		BuildingDTO result = modelMapper.map(entity, BuildingDTO.class);
		
		List<String> rentAreas = entity.getRentAreas().stream()
				.map(RentAreaEntity::getValue)
				.map(item -> item.toString()).collect(Collectors.toList());
		if(rentAreas.size() > 0) {
			result.setRentArea(StringUtils.join(rentAreas, ","));
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
