package com.mrhenry.repository;

import java.util.List;

import com.mrhenry.builder.BuildingSearchBuilder;
import com.mrhenry.entity.BuildingEntity;
import com.mrhenry.paging.Pageable;

public interface IBuildingRepository extends GenericJDBC<BuildingEntity>{
	List<BuildingEntity> findAll(BuildingSearchBuilder builder, Pageable pageable);
	Integer countAll(BuildingSearchBuilder builder);
}
