package com.microservices.accounts.controller;

import com.microservices.accounts.dto.CustomerDetailsDto;
import com.microservices.accounts.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
public class CustomersController {

    private final ICustomerService iCustomerService;

    @Autowired
    public CustomersController(ICustomerService iCustomerService) {
        this.iCustomerService = iCustomerService;
    }

    @GetMapping("/get-customer-info")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestParam("phoneNumber") String phoneNumber) {
        try {
            CustomerDetailsDto customerDetailsDto = iCustomerService.customerDetailsDto(phoneNumber);
            return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomerDetailsDto());
        }
    }
}
