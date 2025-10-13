package com.hotel.bf.dto.mapper;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotel.bf.domain.Trace;
import com.hotel.bf.dto.TraceDto;

/**
 * Mapper for the entity Site and its DTO SiteDTO.
 */
@Component
public class TraceMapper {
     @Autowired
    UserMapper mapper;
    public TraceDto toDto(Trace trace) {
        return TraceDto.builder()
                .id(trace.getId())
                .action(trace.getAction())
                .user(mapper.toDto(trace.getUser()))
                .dateSaisie(trace.getDateSaisie())
                .objet(trace.getObjet())
                .build();
    }

   /*  public Filiale toEntity(FilialeDto filialeDto) {
        return Filiale.builder()
                .id(filialeDto.getId())
                .pays(filialeDto.getPays())
                .nom(filialeDto.getNom())
                .codeBanque(filialeDto.getCodeBanque())
                .code(filialeDto.getCode())
                .deleted(filialeDto.getIsDeleted())
                
                .build();
    } */

    public List<TraceDto> toDtos(List<Trace> traces) {
        return traces.stream().map(this::toDto).toList();
    }

   /*  public List<Filiale> toEntities(List<FilialeDto> filialeDtos) {
        return filialeDtos.stream().map(this::toEntity).toList();
    } */
}
