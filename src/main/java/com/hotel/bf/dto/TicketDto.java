package com.hotel.bf.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import com.hotel.bf.domain.enums.TicketStatus;
import java.time.Instant;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class TicketDto extends AbstractAuditEntityDto {

    private Long id;

    @NotNull
    private String ticketTitle;

    @NotNull
    private String ticketDesc;

    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;

    private Long userId;
}
