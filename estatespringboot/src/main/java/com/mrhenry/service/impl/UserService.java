package com.mrhenry.service.impl;

import com.mrhenry.converter.UserConverter;
import com.mrhenry.dto.UserDTO;
import com.mrhenry.repository.UserRepository;
import com.mrhenry.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserConverter userConverter;

    @Override
    public List<UserDTO> findAllByBuildingId(Long buildingId, Pageable pageable) {
        List<UserDTO> reuslts = userRepository.findAllByBuildingId(buildingId, pageable).stream()
                .map(item -> userConverter.convertToDTO(item)).collect(Collectors.toList());
        return reuslts;
    }

    @Override
    public Integer countAllExcludeAdmin(Long buildingId) {
        return userRepository.countAllExcludeAdmin(buildingId).intValue();
    }
}
