package com.hotel.bf.dto.mapper;

import com.hotel.bf.domain.Nationalite;
import com.hotel.bf.dto.NationaliteDto;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Nationalite and its DTO NationaliteDto.
 */

@Mapper(componentModel = "spring")
public interface NationaliteMapper extends EntityMapper<NationaliteDto, Nationalite> {}
