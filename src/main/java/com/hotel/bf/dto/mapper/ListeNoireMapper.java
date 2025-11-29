package com.hotel.bf.dto.mapper;
import com.hotel.bf.domain.ListeNoire;
import com.hotel.bf.dto.ListeNoireDto;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Site and its DTO ListeNoireDTO.
 */

@Mapper(componentModel = "spring")
public interface ListeNoireMapper extends EntityMapper<ListeNoireDto, ListeNoire> {

}
