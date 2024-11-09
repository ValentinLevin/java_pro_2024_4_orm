package com.example.mapper;

import com.example.dto.ShelterDTO;
import com.example.model.ShelterModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShelterMapper {
    ShelterMapper INSTANCE = Mappers.getMapper(ShelterMapper.class);

    ShelterModel toEntity(ShelterDTO shelterDTO);
    ShelterDTO toDTO(ShelterModel shelter);
}
