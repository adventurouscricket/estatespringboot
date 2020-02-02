package com.mrhenry.repository;

import com.mrhenry.entity.RentAreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentAreaRepository extends JpaRepository<RentAreaEntity, Long>{
    void deleteRentAreaEntitiesByBuildingId(Long buildingId);
}
