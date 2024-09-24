package com.microservices.cards.controller;

import com.microservices.cards.constants.CardsConstants;
import com.microservices.cards.dto.CardsDto;
import com.microservices.cards.dto.payload.ResponseDto;
import com.microservices.cards.service.ICardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/card")
public class CardsController {
    private final ICardsService iCardsService;

    @Autowired
    public CardsController(ICardsService iCardsService) {
        this.iCardsService = iCardsService;
    }

    @PostMapping("/create-card")
    public ResponseEntity<ResponseDto> addCard(@RequestParam String mobilePhone) {
        iCardsService.createCard(mobilePhone);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
    }

    @GetMapping("/fetch-card")
    public ResponseEntity<CardsDto> fetchCard(@RequestParam String mobilePhone) {
        return ResponseEntity.status(HttpStatus.OK).body(iCardsService.fetchCard(mobilePhone));
    }

    @PostMapping("/update-card")
    public ResponseEntity<ResponseDto> updateCard(@RequestBody CardsDto cardsDto) {
        boolean updated = iCardsService.updateCard(cardsDto);
        return ResponseEntity.status(updated ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDto(
                        updated ? CardsConstants.STATUS_200 : CardsConstants.STATUS_417,
                        updated ? CardsConstants.MESSAGE_200 : CardsConstants.MESSAGE_417_UPDATE)
                );
    }
}