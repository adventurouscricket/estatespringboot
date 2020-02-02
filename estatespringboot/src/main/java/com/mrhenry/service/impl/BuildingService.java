package com.mrhenry.service.impl;

import com.mrhenry.builder.BuildingSearchBuilder;
import com.mrhenry.converter.BuildingConverter;
import com.mrhenry.dto.BuildingDTO;
import com.mrhenry.entity.BuildingEntity;
import com.mrhenry.entity.RentAreaEntity;
import com.mrhenry.entity.UserEntity;
import com.mrhenry.repository.BuildingRepository;
import com.mrhenry.repository.RentAreaRepository;
import com.mrhenry.service.IBuildingService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuildingService implements IBuildingService{

	@Autowired
	private BuildingRepository buildingRepository;

	@Autowired
	private RentAreaRepository rentAreaRepository;
	@Autowired
	private BuildingConverter buildingConverter;
	
	@Override
	@Transactional
	public BuildingDTO save(BuildingDTO building) {
		BuildingEntity buildingEntity = buildingConverter.convertToEntity(building);
		buildingEntity.setType(StringUtils.join(building.getBuildingTypes(), ","));
		
		// insert rent area
		List<RentAreaEntity> rentAreas = new ArrayList<>(); 
		if(StringUtils.isNotBlank(building.getRentArea())) {
			for(String item: building.getRentArea().split(",")) {
				RentAreaEntity rentAreaEntity = new RentAreaEntity();
				rentAreaEntity.setValue(Integer.valueOf(item));
				rentAreaEntity.setBuilding(buildingEntity);
				rentAreaEntity.setCreatedBy("Hello");
				rentAreaEntity.setCreatedDate(new Date());
				
				rentAreas.add(rentAreaEntity);
			}
			buildingEntity.setRentAreas(rentAreas);
		}
		
		buildingEntity = buildingRepository.save(buildingEntity);
		
		return buildingConverter.convertToDTO(buildingEntity);
	}

	@Override
	@Transactional
	public void save(BuildingDTO building, Long id) {
		if (id != null) {
			BuildingEntity buildingEntity = buildingRepository.findById(id).get();
			buildingEntity = buildingConverter.convertToEntity(building);
			buildingEntity.setId(id);
			buildingEntity.setCreatedDate(buildingEntity.getCreatedDate());
			buildingEntity.setCreatedBy(buildingEntity.getCreatedBy());
			buildingEntity.setType(StringUtils.join(building.getBuildingTypes(), ","));

			// insert rent area
			List<RentAreaEntity> rentAreas = new ArrayList<>();
			if (StringUtils.isNotBlank(building.getRentArea())) {
				for (String item : building.getRentArea().split(",")) {
					RentAreaEntity rentAreaEntity = new RentAreaEntity();
					rentAreaEntity.setValue(Integer.valueOf(item));
					rentAreaEntity.setBuilding(buildingEntity);
					rentAreaEntity.setCreatedBy("Hello");
					rentAreaEntity.setCreatedDate(new Date());

					rentAreas.add(rentAreaEntity);
				}
				buildingEntity.setRentAreas(rentAreas);
			}

			buildingRepository.save(buildingEntity);
		}
	}

	@Override
	@Transactional
	public void delete(Long[] ids) {
		for (Long id : ids) {

			//delete RentArea in @OneToMany
			rentAreaRepository.deleteRentAreaEntitiesByBuildingId(id);

			//delete Assignment in @ManyToMany
			BuildingEntity buildingEntity = buildingRepository.findById(id).get();
			for(UserEntity user: buildingEntity.getStaffs()) {
				user.getBuildings().remove(buildingEntity);
			}

			buildingRepository.deleteById(id);
		}
	}

	@Override
    public List<BuildingDTO> findAll(BuildingSearchBuilder builder, Pageable pageable) {
		List<BuildingDTO> results= buildingRepository.findAll(builder, pageable).stream()
				.map(item -> buildingConverter.convertToDTO(item)).collect(Collectors.toList());
		return results;
    }

	@Override
	public Integer countAll(BuildingSearchBuilder builder) {
		return buildingRepository.countAll(builder).intValue();
	}

	@Override
	public BuildingDTO findById(Long id) {
		return buildingConverter.convertToDTO(buildingRepository.findById(id).get());
	}
}
