package com.mrhenry.service;

import com.mrhenry.dto.UserDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    List<UserDTO> findAllByBuildingId(Long buildingId, Pageable pageable);
    Integer countAllExcludeAdmin(Long buildingId);
}
