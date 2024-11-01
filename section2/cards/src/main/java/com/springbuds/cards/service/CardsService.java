package com.springbuds.cards.service;

import com.springbuds.cards.dto.CardsDto;

public interface CardsService {

    void createCard(String mobileNumber);

    CardsDto fetchCard(String mobileNumber);

    CardsDto updateCard(CardsDto cardsDto);

    CardsDto deleteCard(String mobileNumber);
}
