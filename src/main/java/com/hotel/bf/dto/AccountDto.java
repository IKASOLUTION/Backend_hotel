package com.hotel.bf.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDto {

    @NotNull(message = "Login cannot be null.")
    @Size(min = 1, max = 50, message = "Login cannot be empty.")
    private String login;

    @NotNull(message = "Password cannot be null.")
    @Size(min = 4, max = 100, message = "Password number of characters range not respected.")
    private String password;

}
