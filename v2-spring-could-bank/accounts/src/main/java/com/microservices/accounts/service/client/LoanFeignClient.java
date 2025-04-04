package com.microservices.accounts.service.client;

import com.microservices.accounts.dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loans", fallback = LoanFallback.class)
public interface LoanFeignClient {
    @GetMapping(value = "/api/fetch-loan", consumes = "application/json")
    public ResponseEntity<LoansDto> fetchLoan(@RequestHeader("microbank-correlation-id") String correlationId, @RequestParam String mobileNumber);
}
