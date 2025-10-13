package com.hotel.bf.dto.mapper;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotel.bf.domain.Region;
import com.hotel.bf.dto.RegionDto;

/**
 * Mapper for the entity Site and its DTO SiteDTO.
 */
@Component
public class RegionMapper {
  
    public RegionDto toDto(Region dt) {
        return RegionDto.builder()
                .id(dt.getId())
                .code(dt.getCode())
                .libelle(dt.getLibelle())
                .isDeleted(dt.getDeleted())
                .build();
    }

    public Region toEntity(RegionDto dto) {
        return Region.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .libelle(dto.getLibelle())
                .deleted(dto.getIsDeleted())
                
                .build();
    }

    public List<RegionDto> toDtos(List<Region> dts) {
        return dts.stream().map(this::toDto).toList();
    }

    public List<Region> toEntities(List<RegionDto> dtos) {
        return dtos.stream().map(this::toEntity).toList();
    }
}
