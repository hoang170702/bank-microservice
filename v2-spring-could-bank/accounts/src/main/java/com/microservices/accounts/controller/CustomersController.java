package com.microservices.accounts.controller;

import com.microservices.accounts.dto.CustomerDetailsDto;
import com.microservices.accounts.service.customer.ICustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CustomersController {

    private static final Logger logger = LoggerFactory.getLogger(CustomersController.class);
    private final ICustomerService iCustomerService;

    @Autowired
    public CustomersController(ICustomerService iCustomerService) {
        this.iCustomerService = iCustomerService;
    }

    @GetMapping("/get-customer-info")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(
            @RequestHeader("microbank-correlation-id") String correlationId,
            @RequestParam("phoneNumber") String phoneNumber) {
        try {
            logger.debug("microbank-correlation-id found {}", correlationId);
            CustomerDetailsDto customerDetailsDto = iCustomerService.customerDetailsDto(correlationId, phoneNumber);
            return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomerDetailsDto());
        }
    }
}
