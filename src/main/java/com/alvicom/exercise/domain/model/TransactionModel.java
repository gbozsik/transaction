package com.alvicom.exercise.domain.model;

import com.alvicom.exercise.domain.Account;
import lombok.Getter;

import java.util.List;

@Getter
public class TransactionModel {

    private String accountNumber;
    private String currency;
    private int ammount;
    private Integer axchangeRate;
}
