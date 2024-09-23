package com.microservices.cards.service;

import com.microservices.cards.dto.CardsDto;

public interface ICardsService {

    void createCard(String mobileNumber);

    CardsDto fetchCard(String mobileNumber);


    boolean updateCard(CardsDto cardsDto);


    boolean deleteCard(String mobileNumber);
}