package com.hotel.bf.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import com.hotel.bf.config.audit.EntityAuditAction;
import com.hotel.bf.config.audit.ObjetEntity;
import com.hotel.bf.config.security.SecurityUtils;

import com.hotel.bf.domain.Mailling;

import com.hotel.bf.dto.MaillingDto;
import com.hotel.bf.dto.mapper.MaillingMapper;
import com.hotel.bf.repository.MaillingRepository;
import com.hotel.bf.repository.UserRepository;


import java.time.LocalDate;
import java.util.List;



@Service
@Transactional
@RequiredArgsConstructor
public class MaillingService {
    private final MaillingRepository maillingRepository;
    private final MaillingMapper mapper;
    private final UserRepository userRepository;
    private final TraceService traceService;
    private final EmailService emailService;
   // private final ObjectMapper objectMapper ;
    /**
     * Save ticket.
     *
     * @param filialeDto {@link test.projects.ennov.dto.FilialeDto}
     * @return saved ticket object
     */
    
    private MaillingDto save(final MaillingDto dto) {
        userRepository.findByDeletedFalseAndUsername(SecurityUtils.getCurrentUsername()).ifPresent(user->{
            dto.setEmail(user.getEmail());
           });
          System.out.println("================dto========="+dto.getEmail());
           dto.setDate(LocalDate.now());

          // entityData = dto.getEmails();
                   
             maillingRepository.save(mapper.toEntity(dto));
            if(dto.getId() !=null) {
            traceService.writeAuditEvent( EntityAuditAction.UPDATE, ObjetEntity.Mailling);
           } else {
            traceService.writeAuditEvent( EntityAuditAction.CREATE, ObjetEntity.Mailling);
           } 

            if(!dto.getEmails().isEmpty()) {
                emailService.sendNewMailing(dto.getEmails(), dto.getTitle(), dto.getMessage());

           } 
       
        return dto;
    }

    /**
     * Create new ticket.
     *
     * @param ticketDto {@link test.projects.ennov.dto.TicketDto}
     * @return created ticket object
     */
    public MaillingDto create(final MaillingDto dto) {
        
        dto.setIsDeleted(Boolean.FALSE);
        return save(dto);
    }

    /**
     * Update existing ticket.
     *
     * @param ticketDto {@link test.projects.ennov.dto.TicketDto}
     * @return updated ticket object
     */
    public MaillingDto update(final MaillingDto dto, final long id) {
       
        return save(dto);
    }


    
    /**
     * Fetch all ticket stored in DB.
     *
     * @return list of {@link test.projects.ennov.dto.TicketDto}
     */
    public List<MaillingDto> findAllFiliale(MaillingDto dto) {
        List<Mailling> filiales = maillingRepository.findByDateBetweenAndDeletedFalse(dto.getDateD().minusDays(1), dto.getDateF().plusDays(1));
        if (filiales.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No data found, Please create at least one filiale before.");
        }
        List<MaillingDto> dtos = mapper.toDtos(filiales);
       
        return dtos;
    }


    public MaillingDto findById(Long id) {
       /*  Set<String> emails = new HashSet<>();
            mailRepository.findAllByDeletedFalseAndMaillingId(id).stream().peek(mail->{
                emails.add(mail.getName());
               
            }).collect(Collectors.toList()); */
            MaillingDto dto =  mapper.toDto(maillingRepository.findOneByDeletedFalseAndId(id));
            
            // dto.setEmails(emails);
        return dto;
    }
    

}
