package com.mrhenry.converter;

import com.mrhenry.dto.UserDTO;
import com.mrhenry.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserDTO convertToDTO(UserEntity entity) {
        ModelMapper modelMapper = new ModelMapper();
        UserDTO result = modelMapper.map(entity, UserDTO.class);

        if(entity.getBuildings().size() > 0) {
            result.setChecked("checked");
        }
        return result;
    }
    public UserEntity convertToEntity(UserDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        UserEntity result = modelMapper.map(dto, UserEntity.class);

        return result;
    }
}
