package com.mrhenry.repository.custom;

import com.mrhenry.builder.BuildingSearchBuilder;
import com.mrhenry.entity.BuildingEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BuildingRepositoryCustom {
    List<BuildingEntity> findAll(BuildingSearchBuilder builder, Pageable pageable);
    Long countAll(BuildingSearchBuilder builder);
}
