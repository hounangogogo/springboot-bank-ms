package com.ms.cards.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ms.cards.cofig.CardsServiceConfig;
import com.ms.cards.model.Cards;
import com.ms.cards.model.Customer;
import com.ms.cards.model.Properties;
import com.ms.cards.service.CardsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class CardsController {

    @Autowired
    private CardsService cardsService;

    @Autowired
    private CardsServiceConfig cardsConfig;

    @PostMapping("/myCards")
    public List<Cards> getCardDetails(@RequestBody Customer customer) {
        log.info("getCardDetails() method started");
        return cardsService.getCardDetails(customer.getCustomerId());
    }

    @GetMapping("/cards/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(cardsConfig.getMsg(), cardsConfig.getBuildVersion(),
                cardsConfig.getMailDetails(), cardsConfig.getActiveBranches());
        String jsonStr = ow.writeValueAsString(properties);
        return jsonStr;
    }

}
