package com.hotel.bf.dto.mapper;

import org.mapstruct.Mapper;


import com.hotel.bf.domain.Region;
import com.hotel.bf.dto.RegionDto;

/**
 * Mapper for the entity Site and its DTO SiteDTO.
 */

@Mapper(componentModel = "spring")
public interface RegionMapper extends EntityMapper<RegionDto, Region> {}
