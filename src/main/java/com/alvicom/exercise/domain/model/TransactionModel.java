package com.alvicom.exercise.domain.model;

import lombok.Getter;

@Getter
public class TransactionModel {

    private String accountNumber;
    private String currency;
    private int ammount;
    private Integer exchangeRate;

    @Override
    public String toString() {
        return "\naccountNumber='" + accountNumber + '\'' + "\n" +
                "currency='" + currency + '\'' + "\n"  +
                "ammount=" + ammount + "\n"  +
                "exchangeRate=" + exchangeRate + "\n";
    }
}
