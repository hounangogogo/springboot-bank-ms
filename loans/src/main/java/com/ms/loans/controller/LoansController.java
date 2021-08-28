package com.ms.loans.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ms.loans.config.LoansServiceConfig;
import com.ms.loans.model.Customer;
import com.ms.loans.model.Loans;
import com.ms.loans.model.Properties;
import com.ms.loans.service.LoansService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class LoansController {

    @Autowired
    private LoansService loansService;

    @Autowired
    private LoansServiceConfig loansConfig;

    @PostMapping("/myLoans")
    public List<Loans> getLoansDetails(String correlationId, @RequestBody Customer customer) {
        log.info("getLoansDetails() method from controller");
        List<Loans> loans = loansService.getLoansDetails(customer.getCustomerId());
        return loans;
    }

    @GetMapping("/loans/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(loansConfig.getMsg(), loansConfig.getBuildVersion(),
                loansConfig.getMailDetails(), loansConfig.getActiveBranches());
        String jsonStr = ow.writeValueAsString(properties);
        return jsonStr;
    }
}
