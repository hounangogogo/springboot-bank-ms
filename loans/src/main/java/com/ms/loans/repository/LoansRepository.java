package com.ms.loans.repository;

import com.ms.loans.model.Loans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoansRepository extends JpaRepository<Loans, Integer> {

    List<Loans> findByCustomerIdOrderByStartDtDesc(int customerId);
}
