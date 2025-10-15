package com.hotel.bf.dto.mapper;

import com.hotel.bf.domain.Hotel;
import com.hotel.bf.dto.HotelDto;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Site and its DTO SiteDTO.
 */

@Mapper(componentModel = "spring")
public interface HotelMapper extends EntityMapper<HotelDto, Hotel> {}
