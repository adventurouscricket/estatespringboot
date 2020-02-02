package com.mrhenry.service;

import com.mrhenry.dto.AssignmentBuildingDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAssignmentBuildingService {
    List<AssignmentBuildingDTO> findAllByBuildingId(Long buildingId, Pageable pageable);
    Integer countAllByBuildingId(Long buildingId);
}
