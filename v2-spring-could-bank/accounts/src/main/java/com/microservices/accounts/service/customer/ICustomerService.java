package com.microservices.accounts.service.customer;

import com.microservices.accounts.dto.CustomerDetailsDto;
import org.springframework.web.bind.annotation.RequestHeader;

public interface ICustomerService {
    public CustomerDetailsDto customerDetailsDto(String correlationId, String phoneNumber);
}
