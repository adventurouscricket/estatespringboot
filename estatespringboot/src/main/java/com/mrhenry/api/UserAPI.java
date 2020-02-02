package com.mrhenry.api;

import com.mrhenry.api.output.building.TotalItem;
import com.mrhenry.builder.BuildingSearchBuilder;
import com.mrhenry.dto.UserDTO;
import com.mrhenry.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserAPI {

    @Autowired
    IUserService userService;

    @GetMapping(value = {"/api/user/assignmentbuilding/{id}"})
    public List<UserDTO> findAllByBuildingId(@RequestParam Map<String, Object> buildingQuery, @PathVariable("id") Long id) {

        Pageable pageable = PageRequest.of((Integer.parseInt(buildingQuery.get("page").toString()) -1), Integer.parseInt(buildingQuery.get("maxPageItem").toString()));

        return userService.findAllByBuildingId(id, pageable);
    }

    @GetMapping(value="/api/user/assignment/{id}/count")
    public TotalItem countAllExcludeAdmin(@RequestParam Map<String, Object> buildingQuery, @PathVariable("id") Long id) {
        return new TotalItem(userService.countAllExcludeAdmin(id));
    }
}
