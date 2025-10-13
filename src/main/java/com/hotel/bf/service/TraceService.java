package com.hotel.bf.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Json31;
import com.hotel.bf.config.audit.EntityAuditAction;
import com.hotel.bf.config.audit.ObjetEntity;
import com.hotel.bf.config.security.SecurityUtils;
import com.hotel.bf.config.security.TestUserDetailsService;
import com.hotel.bf.config.security.jwt.AuthTokenFilter;
import com.hotel.bf.config.security.jwt.TokenProvider;
import com.hotel.bf.domain.AbstractAuditEntity;
import com.hotel.bf.domain.Authority;
import com.hotel.bf.domain.EntityAuditEvent;
import com.hotel.bf.domain.MenuAction;
import com.hotel.bf.domain.Trace;
import com.hotel.bf.domain.User;
import com.hotel.bf.dto.AccountDto;
import com.hotel.bf.dto.JWTToken;
import com.hotel.bf.dto.TicketDto;
import com.hotel.bf.dto.TraceDto;
import com.hotel.bf.dto.UserDto;
import com.hotel.bf.dto.mapper.TraceMapper;
import com.hotel.bf.dto.mapper.UserMapper;
import com.hotel.bf.repository.TraceRepository;
import com.hotel.bf.repository.UserRepository;
import com.hotel.bf.service.util.RandomUtil;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class TraceService {
    @Autowired
    private  TraceMapper mapper;
    @Autowired
    private  TraceRepository traceRepository;
   
    @Autowired
    private   UserRepository userRepository; 

    public List<TraceDto> findTraceByPeriode(LocalDate date1,LocalDate date2) {
        // List<Trace> traces = traceRepository.findByDateSaisieBetweenAndDeletedFalse(date1.plusDays(-1L), date2.plusDays(1L));
        // findDetail(traces.get(0).getId());
        System.out.println("==============date1=="+date1+ "============="+date2);
        System.out.println("==============date1=="+date1.atStartOfDay()+ "============="+date2.atStartOfDay());
        return mapper.toDtos(traceRepository.findByDateSaisieBetweenAndDeletedFalse(date1.atStartOfDay(), date2.atStartOfDay()));
    }

  
    @Async
    public Trace saveTrace( EntityAuditAction action, ObjetEntity objet ) {
        
        Trace trace = new Trace();
        trace.setUser(userRepository.findTop1ByDeletedFalseAndUsername(SecurityUtils.getCurrentUsername()));;
        trace.setObjet(objet.value());
        trace.setAction(action.value());
        trace.setDateSaisie(LocalDateTime.now()); 
        trace.setDeleted(false); 
        traceRepository.save(trace);
      
        return trace;
       
    }

    public TraceDto findDetail(Long id) {
        Optional<Trace> trace = traceRepository.findById(id);
       

        return mapper.toDto(trace.get());
    }
    
    

    @Async
    public void writeAuditEvent( EntityAuditAction action,  ObjetEntity objet) {
       // log.debug("-------------- Post {} audit  --------------", action.value());
        try {
            Trace auditedEntity = saveTrace( action, objet);
            if (auditedEntity != null) {
                traceRepository.save(auditedEntity);
            }
        } catch (Exception e) {
            log.error("Exception while persisting audit entity for {} error: {}", e);
        }
    }


}