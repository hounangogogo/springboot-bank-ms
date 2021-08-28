package com.ms.loans.controller;

import com.ms.loans.model.Customer;
import com.ms.loans.model.Loans;
import com.ms.loans.service.LoansService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class LoansController {

    @Autowired
    private LoansService loansService;


    @PostMapping("/myLoans")
    public List<Loans> getLoansDetails(String correlationId, @RequestBody Customer customer) {
        log.info("getLoansDetails() method from controller");
        List<Loans> loans = loansService.getLoansDetails(customer.getCustomerId());
        return loans;
    }
}
