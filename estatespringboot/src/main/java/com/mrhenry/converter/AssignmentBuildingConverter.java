package com.mrhenry.converter;

import com.mrhenry.dto.AssignmentBuildingDTO;
import com.mrhenry.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AssignmentBuildingConverter {

    public AssignmentBuildingDTO convertToDTO(UserEntity entity) {
        ModelMapper modelMapper = new ModelMapper();
        AssignmentBuildingDTO result = modelMapper.map(entity, AssignmentBuildingDTO.class);

        if(entity.getBuildings().size() > 0) {
            result.setChecked("checked");
        }
        return result;
    }
    public UserEntity convertToEntity(AssignmentBuildingDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        UserEntity result = modelMapper.map(dto, UserEntity.class);

        return result;
    }
}
