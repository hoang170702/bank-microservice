package com.microservices.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CustomerDto {
    @NotEmpty(message = "Name can't be empty or null")
    private String name;
    @NotEmpty(message = "Email can't be empty or null")
    private String email;
    @NotEmpty(message = "Phone number can't be empty or null")
    private String phoneNumber;
    AccountsDto accountsDto;
}
