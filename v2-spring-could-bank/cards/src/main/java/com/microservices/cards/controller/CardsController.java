package com.microservices.cards.controller;

import com.microservices.cards.constants.CardsConstants;
import com.microservices.cards.dto.CardsContactInfoDto;
import com.microservices.cards.dto.CardsDto;
import com.microservices.cards.dto.payload.ResponseDto;
import com.microservices.cards.service.ICardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/card")
public class CardsController {
    private final ICardsService iCardsService;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private CardsContactInfoDto cardsContactInfoDto;

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

    @PostMapping("/delete-card")
    public ResponseEntity<ResponseDto> deleteCard(@RequestParam String mobilePhone) {
        boolean deleted = iCardsService.deleteCard(mobilePhone);
        return ResponseEntity.status(deleted ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDto(
                        deleted ? CardsConstants.STATUS_200 : CardsConstants.STATUS_417,
                        deleted ? CardsConstants.MESSAGE_200 : CardsConstants.MESSAGE_417_DELETE)
                );
    }

    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }


    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    @GetMapping("/contact-info")
    public ResponseEntity<CardsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardsContactInfoDto);
    }
}