package com.microservices.accounts.service.accounts;

import com.microservices.accounts.dto.CustomerDto;

import java.util.Optional;

public interface IAccountService {
    void createAccount(CustomerDto customerDto);
    Optional<CustomerDto> fetchAccount(String phoneNumber);
    boolean updateAccount(CustomerDto customerDto);
    boolean deleteAccount(String phoneNumber);
}
