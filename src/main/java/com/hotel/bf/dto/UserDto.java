package com.hotel.bf.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import com.hotel.bf.domain.Profil;

import static com.hotel.bf.config.AppConstants.LOGIN_REGEX;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class UserDto extends AbstractAuditEntityDto {

    private Long id;

    @Size(min = 1, max = 50)
    @Pattern(regexp = LOGIN_REGEX)
    @NotEmpty(message = "Username is mandatory")
    @NotNull(message = "Username cannot be null")
    private String login;

    @Email(message = "Invalid email")
    @Size(max = 100)
    @NotEmpty(message = "Email is mandatory")
    @NotNull(message = "Email cannot be null")
    private String emailAddress;

    private String password;
    private String nom;
    private String prenom;
    private boolean activated;
    private String activationKey;
    private String resetKey;
    private String langKey;
    private Profil profil;
    private Set<String> authorities;
    private Boolean passChange;

}
