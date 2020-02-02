package com.mrhenry.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrhenry.converter.BuildingConverter;
import com.mrhenry.dto.BuildingDTO;
import com.mrhenry.entity.BuildingEntity;
import com.mrhenry.entity.RentAreaEntity;
import com.mrhenry.repository.IBuildingRepository;
import com.mrhenry.repository.IRentAreaRepository;
import com.mrhenry.repository.impl.BuildingRepository;
import com.mrhenry.repository.impl.RentAreaRepository;
import com.mrhenry.service.IBuildingService;
import com.mrhenry.utils.HttpClientUtil;

public class BuildingService implements IBuildingService{

	final static Logger logger = Logger.getLogger(BuildingService.class);

//	@Inject
	private IBuildingRepository buildingRepository;

//	@Inject
	private IRentAreaRepository rentAreaRepository;

//	@Inject
	private BuildingConverter buildingConverter;

	public BuildingService() {
		if(buildingRepository == null) {
			buildingRepository = new BuildingRepository();
		}

		if(rentAreaRepository == null) {
			rentAreaRepository = new RentAreaRepository();
		}

		if(buildingConverter == null) {
			buildingConverter = new BuildingConverter();
		}
	}

	@Override
	public BuildingDTO save(BuildingDTO building) {
		BuildingEntity buildingEntity = buildingConverter.convertToEntity(building);
		buildingEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		buildingEntity.setCreatedBy("Hello");
		buildingEntity.setType(StringUtils.join(building.getBuildingTypes(), ","));


		Long id = buildingRepository.insert(buildingEntity);

		// insert rent area
		if(StringUtils.isNotBlank(building.getRentArea())) {
			for(String item: building.getRentArea().split(",")) {
				RentAreaEntity rentAreaEntity = new RentAreaEntity();
				rentAreaEntity.setBuildingId(id);
				rentAreaEntity.setValue(item);
				rentAreaEntity.setCreatedBy("Hello");
				rentAreaEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));

				rentAreaRepository.insert(rentAreaEntity);
			}
		}
		return buildingConverter.convertToDTO(buildingRepository.findById(id));
	}
	
	/* JSP-SERVLET
	 * @Override public Integer countAll(BuildingSearchBuilder builder) { //call api
	 * return buildingRepository.countAll(builder); }
	 * 
	 * @Override public BuildingDTO findById(Long id) { return
	 * buildingConverter.convertToDTO(buildingRepository.findById(id)); }
	 * 
	 * @Override public List<BuildingDTO> findAll(BuildingSearchBuilder builder,
	 * Pageable pageable) { List<BuildingDTO> results=
	 * buildingRepository.findAll(builder, pageable).stream() .map(item ->
	 * buildingConverter.convertToDTO(item)).collect(Collectors.toList()); return
	 * results; }
	 */

	@Override
	public List<BuildingDTO> findAll(String url) {
		String result = HttpClientUtil.httpGet(url);
		try {
			return Arrays.asList(new ObjectMapper().readValue(result, BuildingDTO[].class));
		} catch (IOException e) {
			logger.info("READ VALUE: " +e.getMessage(),e);
		}
		return new ArrayList<>();
	}

	@Override
	public void update(BuildingDTO building, Long id) {
		BuildingEntity oldBuilding = buildingRepository.findById(id);
		BuildingEntity buildingEntity = buildingConverter.convertToEntity(building);

		buildingEntity.setCreatedBy(oldBuilding.getCreatedBy());
		buildingEntity.setCreatedDate(oldBuilding.getCreatedDate());
		buildingEntity.setModifiedBy("Modified");
		buildingEntity.setModifiedDate(new Timestamp(System.currentTimeMillis()));

		buildingEntity.setType(StringUtils.join(building.getBuildingTypes(), ","));


		buildingRepository.update(buildingEntity, id);

		// overwrite rent area
		if(StringUtils.isNotBlank(building.getRentArea())) {

			//delete rent area
			String sql = "DELETE FROM rentarea WHERE buildingid = ?";
			rentAreaRepository.delete(id, sql);

			for(String item: building.getRentArea().split(",")) {
				RentAreaEntity rentAreaEntity = new RentAreaEntity();
				rentAreaEntity.setBuildingId(id);
				rentAreaEntity.setValue(item);
				rentAreaEntity.setCreatedBy("Hello");
				rentAreaEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));

				rentAreaRepository.insert(rentAreaEntity);
			}
		}
	}

	@Override
	public void delete(Long[] ids) {
		String sql = "DELETE FROM rentarea WHERE buildingid = ?";
		for(Long id: ids) {
			rentAreaRepository.delete(id, sql);
			buildingRepository.delete(id, null);
		}
	}

	@Override
	public Integer countAll(String url) {
		String result = HttpClientUtil.httpGet(url);
		BuildingDTO buildingDTO = null;
		try {
			buildingDTO = new ObjectMapper().readValue(result, BuildingDTO.class);
			return buildingDTO.getTotalItem();
		} catch (IOException e) {
			logger.info("COUNT ALL SERVICE: "+e.getMessage(),e);
		}
		return 0;
	}

	@Override
	public BuildingDTO findById(String url) {
		String result = HttpClientUtil.httpGet(url);
		try {
			return new ObjectMapper().readValue(result, BuildingDTO.class);
		} catch (IOException e) {
			logger.info("READ VALUE: " +e.getMessage(),e);
		}
		return new BuildingDTO();
	}
}
