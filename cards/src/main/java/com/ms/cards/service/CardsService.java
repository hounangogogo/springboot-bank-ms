package com.ms.cards.service;


import com.ms.cards.model.Cards;
import com.ms.cards.repository.CardsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CardsService {

    @Autowired
    private CardsRepository cardsRepository;

    public List<Cards> getCardDetails(int customerId) {
        return cardsRepository.findByCustomerId(customerId);
    }
}