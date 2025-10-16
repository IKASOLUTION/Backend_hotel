package com.hotel.bf.dto.mapper;

import com.hotel.bf.domain.Commune;
import com.hotel.bf.domain.Hotel;
import com.hotel.bf.dto.CommuneDto;
import com.hotel.bf.dto.HotelDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity Site and its DTO SiteDTO.
 */

@Mapper(componentModel = "spring")
public interface HotelMapper extends EntityMapper<HotelDto, Hotel> {
    @Mapping(target = "commune", source = "commune", qualifiedByName = "communeId")
    HotelDto toDto(Hotel s);

    @Named("communeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "libelle", source = "libelle")
    @Mapping(target = "province", source = "province")
    CommuneDto toDtoCommuneId(Commune commune);
}
