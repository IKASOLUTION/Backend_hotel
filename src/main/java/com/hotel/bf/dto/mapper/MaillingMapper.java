package com.hotel.bf.dto.mapper;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.Mapping;

import com.hotel.bf.domain.Mailling;
import com.hotel.bf.dto.MaillingDto;



/**
 * Mapper for the entity Site and its DTO SiteDTO.
 */
@Component
public class MaillingMapper {
    public MaillingDto toDto(Mailling mailling) {
        return MaillingDto.builder()
                .id(mailling.getId())
                .title(mailling.getTitle())
                .message(mailling.getMessage())
                .date(mailling.getDate())
                .isDeleted(mailling.getDeleted())
                .email(mailling.getEmail())
                .emails(Set.of(mailling.getEmails()))

                .build();
    }

    public Mailling toEntity(MaillingDto maillingDto) {
        return Mailling.builder()
                .id(maillingDto.getId())
                .title(maillingDto.getTitle())
                .message(maillingDto.getMessage())
                .date(maillingDto.getDate())
                .email(maillingDto.getEmail())
                .emails(maillingDto.getEmails().toString())
                .deleted(maillingDto.getIsDeleted())
                .build();
    }

    public List<MaillingDto> toDtos(List<Mailling> maillings) {
        return maillings.stream().map(this::toDto).toList();
    }

    public List<Mailling> toEntities(List<MaillingDto> maillingDtos) {
        return maillingDtos.stream().map(this::toEntity).toList();
    }
}
