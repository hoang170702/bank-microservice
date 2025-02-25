package com.microservices.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailsDto {
    @NotEmpty(message = "Name can't be empty or null")
    private String name;
    @NotEmpty(message = "Email can't be empty or null")
    private String email;
    @NotEmpty(message = "Phone number can't be empty or null")
    private String phoneNumber;
    private AccountsDto accountsDto;
    private CardsDto cardsDto;
    private LoansDto loansDto;
}