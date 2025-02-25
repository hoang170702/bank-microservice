package com.microservices.accounts.service.client;

import com.microservices.accounts.dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loans")
public interface LoanFeignClient {
    @GetMapping(value = "/api/loans/fetch-loan", consumes = "application/json")
    public ResponseEntity<LoansDto> fetchLoan(@RequestParam String mobileNumber);
}
