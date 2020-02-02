package com.mrhenry.api;

import com.mrhenry.api.output.building.TotalItem;
import com.mrhenry.dto.AssignmentBuildingDTO;
import com.mrhenry.service.IAssignmentBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AssignmentBuildingAPI {

    @Autowired
    IAssignmentBuildingService assignmentBuildingService;

    @GetMapping(value = {"/api/building/assignment/{id}"})
    public List<AssignmentBuildingDTO> findAllByBuildingId(@RequestParam Map<String, Object> buildingQuery, @PathVariable("id") Long id) {

        Pageable pageable = PageRequest.of((Integer.parseInt(buildingQuery.get("page").toString()) -1), Integer.parseInt(buildingQuery.get("maxPageItem").toString()));

        return assignmentBuildingService.findAllByBuildingId(id, pageable);
    }

    @GetMapping(value="/api/building/assignment/{id}/count")
    public TotalItem countAllExcludeAdmin(@RequestParam Map<String, Object> buildingQuery, @PathVariable("id") Long id) {
        return new TotalItem(assignmentBuildingService.countAllByBuildingId(id));
    }
}
