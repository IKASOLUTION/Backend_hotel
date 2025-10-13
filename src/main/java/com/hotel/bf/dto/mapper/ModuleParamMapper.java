package com.hotel.bf.dto.mapper;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hotel.bf.domain.ModuleParam;
import com.hotel.bf.dto.ModuleParamDto;

/**
 * Mapper for the entity  ModuleParam and its DTO.
 */
@Component
public class ModuleParamMapper {

    public ModuleParamDto toDto(ModuleParam moduleParam) {
    return ModuleParamDto.builder()
            .id(moduleParam.getId())
            .moduleParamLibelle(moduleParam.getModuleParamLibelle())
            .moduleParamCode(moduleParam.getModuleParamCode())
            .isDeleted(moduleParam.getDeleted())
            .build();
}

public ModuleParam toEntity(ModuleParamDto moduleParamDto) {
    return ModuleParam.builder()
            .id(moduleParamDto.getId())
            .moduleParamLibelle(moduleParamDto.getModuleParamLibelle())
            .moduleParamCode(moduleParamDto.getModuleParamCode())
            .deleted(moduleParamDto.getIsDeleted())
            .build();
}

public List<ModuleParamDto> toDtos(List<ModuleParam> moduleParams) {
    return moduleParams.stream().map(this::toDto).toList();
}

public List<ModuleParam> toEntities(List<ModuleParamDto> moduleParamDtos) {
    return moduleParamDtos.stream().map(this::toEntity).toList();
}

}
