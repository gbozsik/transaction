package com.alvicom.exercise.Controller;

import com.alvicom.exercise.domain.model.TransactionModel;
import com.alvicom.exercise.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Component
@RestController
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping(value = "/saveTransaction")
    public ResponseEntity<TransactionModel> saveTransaction(@RequestBody TransactionModel transactionModel) {
        List<Map<String, List<TransactionModel>>> reportMapList = transactionService.saveTransaction(transactionModel);
        return new ResponseEntity(reportMapList, HttpStatus.OK);
    }

}
