package com.hotel.bf.dto.mapper;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotel.bf.domain.Commune;
import com.hotel.bf.domain.Province;
import com.hotel.bf.dto.CommuneDto;
import com.hotel.bf.dto.ProvinceDto;

import com.hotel.bf.domain.Region;

/**
 * Mapper for the entity Site and its DTO SiteDTO.
 */
@Component
public class CommuneMapper {
    @Autowired
    ProvinceMapper provinceMapper;

    public CommuneDto toDto(Commune dt) {
        return CommuneDto.builder()
                .id(dt.getId())
                .libelle(dt.getLibelle())
                .province(provinceMapper.toDto(dt.getProvince()))
                .isDeleted(dt.getDeleted())
                .build();
    }

    public Commune toEntity(CommuneDto dto) {
        return Commune.builder()
                .id(dto.getId())
                .libelle(dto.getLibelle())
                .deleted(dto.getIsDeleted())
                .province(provinceMapper.toEntity(dto.getProvince()))
                .build();
    }

    public List<CommuneDto> toDtos(List<Commune> dts) {
        return dts.stream().map(this::toDto).toList();
    }

    public List<Commune> toEntities(List<CommuneDto> dtos) {
        return dtos.stream().map(this::toEntity).toList();
    }
}
