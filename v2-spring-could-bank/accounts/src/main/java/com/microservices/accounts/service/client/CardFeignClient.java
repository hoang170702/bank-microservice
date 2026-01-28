package com.microservices.accounts.service.client;

import com.microservices.accounts.dto.CardsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cards",url = "http://cards:9000", fallback = CardFallback.class)
public interface CardFeignClient {
    @GetMapping(value = "/api/fetch-card", consumes = "application/json")
    ResponseEntity<CardsDto> fetchCard(@RequestHeader("microbank-correlation-id") String correlationId, @RequestParam String mobilePhone);
}
