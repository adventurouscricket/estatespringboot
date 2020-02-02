package com.mrhenry.repository;

import com.mrhenry.entity.BuildingEntity;
import com.mrhenry.repository.custom.BuildingRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<BuildingEntity, Long>, BuildingRepositoryCustom {
}
