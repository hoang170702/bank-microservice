package com.microservices.cards.service;

import com.microservices.cards.dto.CardsDto;
import com.microservices.cards.entity.Cards;
import com.microservices.cards.repository.CardRepository;

import java.util.Optional;

public class CardsService implements ICardsService {
    private final CardRepository cardRepository;

    public CardsService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public void createCard(String mobileNumber) {

    }

    @Override
    public CardsDto fetchCard(String mobileNumber) {
        return null;
    }

    @Override
    public boolean updateCard(CardsDto cardsDto) {
        return false;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        return false;
    }
}
