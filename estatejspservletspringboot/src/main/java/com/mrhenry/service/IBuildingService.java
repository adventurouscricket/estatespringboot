package com.mrhenry.service;

import java.util.List;

import com.mrhenry.dto.BuildingDTO;

public interface IBuildingService {
	BuildingDTO save(BuildingDTO building);
	void update(BuildingDTO building, Long id);
//	List<BuildingDTO> findAll(BuildingSearchBuilder builder, Pageable pageable);
//	BuildingDTO findById(Long id);
	void delete(Long[] ids);
//	Integer countAll(BuildingSearchBuilder builder);
	
	List<BuildingDTO> findAll(String url);
	Integer countAll(String url);
	BuildingDTO findById(String url);
}
