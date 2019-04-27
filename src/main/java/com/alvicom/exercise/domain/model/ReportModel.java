package com.alvicom.exercise.domain.model;

import com.alvicom.exercise.domain.model.TransactionModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class ReportModel {

    private String accountNumber;
    private List<TransactionModel> transactionModel;

}
