package com.microservices.cards.service;

import com.microservices.cards.constants.CardsConstants;
import com.microservices.cards.dto.CardsDto;
import com.microservices.cards.entity.Cards;
import com.microservices.cards.exception.CardAlreadyExistsException;
import com.microservices.cards.exception.ResourceNotFoundException;
import com.microservices.cards.mapper.CardMapper;
import com.microservices.cards.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class CardsService implements ICardsService {
    private final CardRepository cardRepository;

    public CardsService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> existsCard = cardRepository.findByMobileNumber(mobileNumber);
        if (existsCard.isPresent()) {
            throw new CardAlreadyExistsException("phone number already exists: " + mobileNumber);
        }
        cardRepository.save(createNewCard(mobileNumber));
    }

    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();

        newCard.setMobileNumber(mobileNumber);
        newCard.setCardNumber(Long.toString(createCardNumber()));
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);

        return newCard;
    }

    private Long createCardNumber() {
        return 1000000000L + new Random().nextInt(900000000);
    }

    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Optional<Cards> card = cardRepository.findByMobileNumber(mobileNumber);
        return card.map(cards -> CardMapper.mapToCardsDto(cards, new CardsDto())).orElseThrow(() -> new ResourceNotFoundException("Card", "Phone number", mobileNumber));
    }

    @Override
    public boolean updateCard(CardsDto cardsDto) {
        boolean isUpdate = false;
        Optional<Cards> card = cardRepository.findByMobileNumber(cardsDto.getMobileNumber());
        if (card.isPresent()) {
            Cards cardUpdate = CardMapper.mapToCards(cardsDto, card.get());
            cardUpdate.setUpdatedAt(LocalDateTime.now());
            cardRepository.save(cardUpdate);
            isUpdate = true;
        }
        return isUpdate;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        return false;
    }
}
