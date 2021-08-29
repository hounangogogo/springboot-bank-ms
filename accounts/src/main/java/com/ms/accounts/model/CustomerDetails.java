package com.ms.accounts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetails {
    private Accounts accounts;
    private List<Loans> loans;
    private List<Cards> cards;
}
