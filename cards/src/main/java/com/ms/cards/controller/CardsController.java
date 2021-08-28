package com.ms.cards.controller;

import com.ms.cards.model.Cards;
import com.ms.cards.model.Customer;
import com.ms.cards.service.CardsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class CardsController {

    @Autowired
    private CardsService cardsService;


    @PostMapping("/myCards")
    public List<Cards> getCardDetails(@RequestBody Customer customer) {
        log.info("getCardDetails() method started");
        return cardsService.getCardDetails(customer.getCustomerId());
    }

}
