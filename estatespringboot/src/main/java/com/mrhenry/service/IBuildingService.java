package com.mrhenry.service;

import com.mrhenry.builder.BuildingSearchBuilder;
import com.mrhenry.dto.BuildingDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBuildingService {
	BuildingDTO save(BuildingDTO building);
	void save(BuildingDTO building, Long id);
	void delete(Long[] ids);
	List<BuildingDTO> findAll(BuildingSearchBuilder builder, Pageable pageable);
	Integer countAll(BuildingSearchBuilder builder);
	BuildingDTO findById(Long id);
}
