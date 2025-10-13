package com.hotel.bf.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import static com.hotel.bf.config.AppConstants.LOGIN_REGEX;

@Data
@Getter
@Setter
@Entity
@SuperBuilder
@Table(name = "ennov_users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractAuditEntity implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_generator")
    @SequenceGenerator(name = "users_seq_generator", sequenceName = "ennov_users_sequence",
            initialValue = 1000, allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(min = 1, max = 50)
    @Pattern(regexp = LOGIN_REGEX)
    @NotEmpty(message = "Username is mandatory")
    @NotNull(message = "Username cannot be null")
    @Column(name = "username", length = 50, unique = true, nullable = false)
    private String username;

    @Email
    @Size(max = 100)
    @NotEmpty(message = "Email is mandatory")
    @NotNull(message = "Email cannot be null")
    @Column(name = "email", length = 100, unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "passwd", length = 60)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @NotNull
    @Column(nullable = false)
    private boolean activated;
    

    @Size(min = 2, max = 10)
    @Column(name = "lang_key", length = 10)
    private String langKey;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

     @Column(name = "reset_date")
    private Instant resetDate;
    
   /*  @ManyToOne(cascade = CascadeType.MERGE)
    @JsonIgnoreProperties(value = {"ennov_users"})
    private Agence agence; */
    @Column(name = "pass_change")
    private Boolean passChange;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonIgnoreProperties(value = {"ennov_users"})
    private Profil profil;
    /* @ManyToOne(cascade = CascadeType.MERGE)
    @JsonIgnoreProperties(value = {"ennov_users"})
    private Filiale filiale; */

    @Column(name = "deleted")
    private Boolean deleted; 
    private String verificationToken;
    @NotEmpty
    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(
            name = "ennov_user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    private Set<Authority> authorities;
     private Date expiryDate;

}
