package com.microservices.accounts.service.client;

import com.microservices.accounts.dto.CardsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cards")
public interface CardFeignClient {
    @GetMapping(value = "/api/card/fetch-card", consumes = "application/json")
    public ResponseEntity<CardsDto> fetchCard(@RequestParam String mobilePhone);
}
