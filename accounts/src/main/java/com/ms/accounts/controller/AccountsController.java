package com.ms.accounts.controller;

import com.ms.accounts.model.Accounts;
import com.ms.accounts.model.Customer;
import com.ms.accounts.service.AccountsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AccountsController {

    @Autowired
    AccountsService accountsService;


    @PostMapping("/myAccount")
    public Accounts getAccountDetails(@RequestBody Customer customer) {
        log.info("Get account detail from controller");
        return accountsService.getAccountDetails(customer.getCustomerId());
    }

}
