package com.ms.accounts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ms.accounts.config.AccountsServiceConfig;
import com.ms.accounts.model.Accounts;
import com.ms.accounts.model.Customer;
import com.ms.accounts.model.Properties;
import com.ms.accounts.service.AccountsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AccountsController {

    @Autowired
    AccountsService accountsService;

    @Autowired
    AccountsServiceConfig accountsConfig;


    @PostMapping("/myAccount")
    public Accounts getAccountDetails(@RequestBody Customer customer) {
        log.info("Get account detail from controller");
        return accountsService.getAccountDetails(customer.getCustomerId());
    }


    @GetMapping("/account/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(accountsConfig.getMsg(), accountsConfig.getBuildVersion(),
                accountsConfig.getMailDetails(), accountsConfig.getActiveBranches());
        String jsonStr = ow.writeValueAsString(properties);
        return jsonStr;
    }

}
