package com.ms.loans.service;

import com.ms.loans.model.Loans;
import com.ms.loans.repository.LoansRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LoansService {

    @Autowired
    private LoansRepository loansRepository;

    public List<Loans> getLoansDetails(int customerId) {
        log.info("getLoansDetails from service");
        return loansRepository.findByCustomerIdOrderByStartDtDesc(customerId);
    }
}
