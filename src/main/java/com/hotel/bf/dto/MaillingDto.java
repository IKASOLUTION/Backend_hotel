package com.hotel.bf.dto;

import java.time.LocalDate;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)

public class MaillingDto extends AbstractAuditEntityDto{
    
    private Long id;
    private String title;
    private String message;
    private String code;
    private Set<String> emails;
    private String email;
    private LocalDate date;
    private LocalDate dateD;
    private LocalDate dateF;
  
    
}
