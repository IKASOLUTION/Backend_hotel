package com.hotel.bf.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDto {

   
    private String login;

    private String password;

    private Boolean rememberMe = false;

}
