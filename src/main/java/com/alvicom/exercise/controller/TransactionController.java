package com.alvicom.exercise.controller;

import com.alvicom.exercise.domain.model.TransactionModel;
import com.alvicom.exercise.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping(value = "/saveTransaction")
    public void saveTransaction(@RequestBody TransactionModel transactionModel) {
        transactionService.saveTransaction(transactionModel);
    }

}
