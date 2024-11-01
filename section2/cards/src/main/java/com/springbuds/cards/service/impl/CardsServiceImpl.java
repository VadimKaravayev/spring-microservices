package com.springbuds.cards.service.impl;

import com.springbuds.cards.constants.CardsConstants;
import com.springbuds.cards.dto.CardsDto;
import com.springbuds.cards.entity.Cards;
import com.springbuds.cards.exception.CardAlreadyExistsException;
import com.springbuds.cards.exception.ResourceNotFoundException;
import com.springbuds.cards.mapper.CardsMapper;
import com.springbuds.cards.repository.CardsRepository;
import com.springbuds.cards.service.CardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements CardsService {

    private final CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);
        if (optionalCards.isPresent()) {
            throw new CardAlreadyExistsException("Card already exists with number: " + mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }

    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }

    @Override
    public CardsDto fetchCard(String mobileNumber) {
        return cardsRepository.findByMobileNumber(mobileNumber)
                .map(cards -> CardsMapper.mapToCardsDto(cards, CardsDto.builder().build()))
                .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));

    }

    @Override
    public CardsDto updateCard(CardsDto cardsDto) {
        return cardsRepository.findByCardNumber(cardsDto.getCardNumber()).map(cards -> {
            CardsMapper.mapToCards(cardsDto, cards);
            Cards savedCard = cardsRepository.save(cards);
            return CardsMapper.mapToCardsDto(savedCard, CardsDto.builder().build());
        }).orElseThrow(() -> new ResourceNotFoundException("Card", "cardNumber", cardsDto.getCardNumber()));
    }

    @Override
    public CardsDto deleteCard(String mobileNumber) {
        return cardsRepository.findByMobileNumber(mobileNumber)
                .map(cards -> {
                    cardsRepository.deleteById(cards.getCardId());
                    return CardsMapper.mapToCardsDto(cards, CardsDto.builder().build());
                })
                .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));

    }
}
