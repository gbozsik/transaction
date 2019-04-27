package com.alvicom.exercise.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Account {

    private String accountNumber;
    private String currency;
    private int ballance;

    public Account() {
    }

    public Account(String accountNumber, String currency, int ballance) {
        this.accountNumber = accountNumber;
        this.currency = currency;
        this.ballance = ballance;
    }
}
