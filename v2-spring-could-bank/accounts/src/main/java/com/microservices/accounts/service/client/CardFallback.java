package com.microservices.accounts.service.client;

import com.microservices.accounts.dto.CardsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardFallback implements CardFeignClient {
    @Override
    public ResponseEntity<CardsDto> fetchCard(String correlationId, String mobilePhone) {
        return null;
    }
}


