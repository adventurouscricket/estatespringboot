package com.mrhenry.repository.custom;

import com.mrhenry.entity.UserEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepositoryCustom {
    List<UserEntity> findAllByBuildingId(Long buildingId, Pageable pageable);
    Long countAllExcludeAdmin(Long buildingId);
}
