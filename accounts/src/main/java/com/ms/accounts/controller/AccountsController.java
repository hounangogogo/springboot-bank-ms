package com.ms.accounts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ms.accounts.config.AccountsServiceConfig;
import com.ms.accounts.feignClient.CardsFeignClient;
import com.ms.accounts.feignClient.LoansFeignClient;
import com.ms.accounts.model.*;
import com.ms.accounts.service.AccountsService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class AccountsController {

    @Autowired
    AccountsService accountsService;

    @Autowired
    AccountsServiceConfig accountsConfig;

    @Autowired
    LoansFeignClient loansFeignClient;

    @Autowired
    CardsFeignClient cardsFeignClient;


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

    @PostMapping("/myCustomerDetails")
    @CircuitBreaker(name = "detailsForCustomerSupportApp", fallbackMethod = "myCustomerDetailsFallBack")
    public CustomerDetails myCustomerDetails(@RequestBody Customer customer) {
        log.info("My customerDetails() method started");
        Accounts accounts = accountsService.getAccountDetails(customer.getCustomerId());
        List<Loans> loans = loansFeignClient.getLoansDetails(customer);
        List<Cards> cards = cardsFeignClient.getCardDetails(customer);
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccounts(accounts);
        customerDetails.setLoans(loans);
        customerDetails.setCards(cards);
        log.info("My customerDetails() method ended");
        return customerDetails;
    }

    private CustomerDetails myCustomerDetailsFallBack(Customer customer, Throwable t) {
        Accounts accounts = accountsService.getAccountDetails(customer.getCustomerId());
        List<Loans> loans = loansFeignClient.getLoansDetails(customer);
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccounts(accounts);
        customerDetails.setLoans(loans);
        return customerDetails;
    }


    @PostMapping("/myCustomerCards")
    @Retry(name = "retryForCustomerCards", fallbackMethod = "retryForCustomerCardsFallBack")
    public CustomerDetails customerDetailsWithCards(@RequestBody Customer customer) {
        Accounts accounts = accountsService.getAccountDetails(customer.getCustomerId());
        List<Cards> cards = cardsFeignClient.getCardDetails(customer);
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccounts(accounts);
        customerDetails.setCards(cards);
        return customerDetails;
    }


    public CustomerDetails retryForCustomerCardsFallBack(@RequestBody Customer customer, Throwable t) {
        Accounts accounts = accountsService.getAccountDetails(customer.getCustomerId());
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccounts(accounts);
        return customerDetails;
    }



    @GetMapping("/sayHello")
    @RateLimiter(name="sayHello", fallbackMethod = "sayHelloFallback")
    public String sayHello() {
        return "Hello, from sayHello";
    }

    private String sayHelloFallback(Throwable t) {
        return "hi, from fallback";
    }

}
