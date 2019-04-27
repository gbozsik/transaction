package com.alvicom.exercise.service;

import com.alvicom.exercise.domain.model.TransactionModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface TransactionService {

    Map<String, List<TransactionModel>> saveTransaction(TransactionModel transactionModel);

}
