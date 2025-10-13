package com.hotel.bf.domain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@Builder
@Entity
@Table(name = "ennov_mail")
@NoArgsConstructor
@AllArgsConstructor
public class Mail extends AbstractAuditEntity implements  Serializable {

     @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mail_seq_generator")
    @SequenceGenerator(name = "mail_seq_generator", sequenceName = "ennov_mail_sequence",
            initialValue = 1001, allocationSize = 1)
    private Long id;
    
    @Column(length = 50)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("mails")
    private Mailling mailling;
}
