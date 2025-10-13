package com.hotel.bf.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hotel.bf.config.audit.EntityAuditAction;
import com.hotel.bf.config.audit.ObjetEntity;
import com.hotel.bf.config.security.SecurityUtils;
import com.hotel.bf.domain.MenuAction;
import com.hotel.bf.domain.Profil;
import com.hotel.bf.domain.Trace;
import com.hotel.bf.domain.User;
import com.hotel.bf.domain.enums.TicketStatus;
import com.hotel.bf.dto.FilialeDto;
import com.hotel.bf.dto.MenuActionDto;
import com.hotel.bf.dto.ProfilDto;
import com.hotel.bf.dto.TicketDto;
import com.hotel.bf.dto.mapper.ProfilMapper;
import com.hotel.bf.repository.MenuActionRepository;
import com.hotel.bf.repository.ProfilRepository;
import com.hotel.bf.repository.TraceRepository;
import com.hotel.bf.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfilService {
    private final ProfilRepository profilRepository;
    private final ProfilMapper mapper;
    private final UserRepository userRepository;
    private final TraceRepository traceRepository;
    private final MenuActionRepository menuActionRepository;
    private final TraceService traceService;

    /**
     * Save ticket.
     *
     * @param filialeDto {@link test.projects.ennov.dto.FilialeDto}
     * @return saved ticket object
     */
    private ProfilDto saveProfil(final ProfilDto dto) {
        System.out.println("=========dto=========="+dto);
      
        if(dto.getId() != null) {
            System.out.println("=========dto==1========"+dto);
            Profil profil1 = profilRepository.findOneByDeletedFalseAndId(dto.getId());
            profil1.setMenus(dto.getMenus());
            profil1.setCode(dto.getCode());
            profil1.setLibelle(dto.getLibelle());
            traceService.writeAuditEvent( EntityAuditAction.UPDATE, ObjetEntity.PROFIL);
           } else {
            Profil profil = profilRepository.save(mapper.toEntity(dto));
            System.out.println("=========dto==2========"+dto);
            traceService.writeAuditEvent( EntityAuditAction.CREATE, ObjetEntity.PROFIL);
           }

        return dto;
    }

    /**
     * Create new ticket.
     *
     * @param ticketDto {@link test.projects.ennov.dto.TicketDto}
     * @return created ticket object
     */
    public ProfilDto createProfil(final ProfilDto dto) {
        if (profilRepository.existsByDeletedFalseAndCode(dto.getCode())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "profil already exists.");
        }

        dto.setIsDeleted(Boolean.FALSE);
        
        return saveProfil(dto);
    }

    /**
     * Update existing ticket.
     *
     * @param ticketDto {@link test.projects.ennov.dto.TicketDto}
     * @return updated ticket object
     */
    public ProfilDto update(final ProfilDto dto, final long id) {
        if (!profilRepository.existsByDeletedFalseAndId(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No profil exists with this ID : %d", id));
        }

        if (Objects.isNull(dto.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already created filiale cannot have null ID.");
        }

        if (profilRepository.existsByDeletedFalseAndCodeAndIdNot(dto.getCode(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A profil with the same title already exists.");
        }

        if(userRepository.findTop1ByDeletedFalseAndUsername(SecurityUtils.getCurrentUsername()).getProfil().getId() ==id) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Vous ne pouvez pas modifier vos propes profiles");
 
        }

        dto.setIsDeleted(Boolean.FALSE);
        return saveProfil(dto);
    }


     public List<ProfilDto> deleteAll(List<ProfilDto> dtos) {
        mapper.toEntities(dtos).stream().peek(filiale-> {
            filiale.setDeleted(Boolean.TRUE);
            profilRepository.save(filiale);
            
        }).collect(Collectors.toList());
        return dtos;
     }

    /**
     * Get ticket by id.
     *
     * @param id searched ticket id
     * @return found ticket object
     */
    public ProfilDto findProfil(final long id) {
        if (!profilRepository.existsByDeletedFalseAndId(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No filiale exists with this ID : %d", id));
        }

        return mapper.toDto(profilRepository.findOneByDeletedFalseAndId(id));
    }

    /**
     * Fetch all ticket stored in DB.
     *
     * @return list of {@link test.projects.ennov.dto.TicketDto}
     */
    public List<ProfilDto> findAllProfil() {
        List<Profil> dts = profilRepository.findAllByDeletedFalse();
        if (dts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No data found, Please create at least one filiale before.");
        }
        return mapper.toDtos(dts);
    }

    /**
     * Remove a ticket by id if exists.
     *
     * @param id removed ticket id.
     */
    public void delete(final long id) {
        profilRepository.findTop1ByDeletedFalseAndId(id).ifPresentOrElse(filiale -> {
            filiale.setDeleted(Boolean.TRUE);
            profilRepository.save(filiale);
            traceService.writeAuditEvent( EntityAuditAction.DELETE, ObjetEntity.PROFIL);
        }, () -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Cannot remove filiale with ID : %d", id));
        });
    }

   

    

}
