package com.mrhenry.repository;

import com.mrhenry.entity.UserEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AssignmentBuildingRepository {
    List<UserEntity> findAllByBuildingId(Long buildingId, Pageable pageable);
    Long countAllBuildingId(Long buildingId);
}
