package com.mrhenry.service.impl;

import com.mrhenry.converter.AssignmentBuildingConverter;
import com.mrhenry.dto.AssignmentBuildingDTO;
import com.mrhenry.repository.AssignmentBuildingRepository;
import com.mrhenry.service.IAssignmentBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignmentBuildingServiceImpl implements IAssignmentBuildingService {

    @Autowired
    AssignmentBuildingRepository assignmentBuildingRepository;

    @Autowired
    AssignmentBuildingConverter assignmentBuildingConverter;

    @Override
    public List<AssignmentBuildingDTO> findAllByBuildingId(Long buildingId, Pageable pageable) {
        List<AssignmentBuildingDTO> reuslts = assignmentBuildingRepository.findAllByBuildingId(buildingId, pageable).stream()
                .map(item -> assignmentBuildingConverter.convertToDTO(item)).collect(Collectors.toList());
        return reuslts;
    }

    @Override
    public Integer countAllByBuildingId(Long buildingId) {
        return assignmentBuildingRepository.countAllBuildingId(buildingId).intValue();
    }
}
