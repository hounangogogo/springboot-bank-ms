package com.ms.accounts.service;

import com.ms.accounts.model.Accounts;
import com.ms.accounts.repository.AccountsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountsService {

    @Autowired
    AccountsRepository accountsRepository;

    public Accounts getAccountDetails(int customerId) {
        log.info("Get account detail from service");
        return accountsRepository.findByCustomerId(customerId);
    }
}
