package com.microservices.accounts.service.customer;

import com.microservices.accounts.dto.CustomerDetailsDto;

public interface ICustomerService {
    public CustomerDetailsDto customerDetailsDto(String phoneNumber);
}
