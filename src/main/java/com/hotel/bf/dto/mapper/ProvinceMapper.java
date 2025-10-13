package com.hotel.bf.dto.mapper;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotel.bf.domain.Province;
import com.hotel.bf.dto.ProvinceDto;
import com.hotel.bf.domain.Region;

/**
 * Mapper for the entity Site and its DTO SiteDTO.
 */
@Component
public class ProvinceMapper {
    @Autowired
    private RegionMapper regionMapper;

    public ProvinceDto toDto(Province dt) {
        return ProvinceDto.builder()
                .id(dt.getId())
                .libelle(dt.getLibelle())
                .region(dt.getRegion() != null ? regionMapper.toDto(dt.getRegion()) : null)
                .isDeleted(dt.getDeleted())
                .build();
    }

    public Province toEntity(ProvinceDto dto) {
        return Province.builder()
                .id(dto.getId())
                .libelle(dto.getLibelle())
                .deleted(dto.getIsDeleted())
                .region(regionMapper.toEntity(dto.getRegion()))
                .build();
    }

    public List<ProvinceDto> toDtos(List<Province> dts) {
        return dts.stream().map(this::toDto).toList();
    }

    public List<Province> toEntities(List<ProvinceDto> dtos) {
        return dtos.stream().map(this::toEntity).toList();
    }
}
