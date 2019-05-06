package com.alvicom.exercise.service.imp;

import com.alvicom.exercise.domain.Account;
import com.alvicom.exercise.domain.model.TransactionModel;
import com.alvicom.exercise.service.TransactionService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
public class TransactionServiceImp implements TransactionService {

    private static int countTransactions;
    private Map<String, List<TransactionModel>> reportMap = new HashMap<>();
    private Map<String, Account> accountMap = new HashMap<>();

    @PostConstruct
    private void fillAccountList() {
        Account account1 = new Account("11111111-22222222", "HUF", 150000);
        Account account2 = new Account("22222222-33333333", "USD", 1230);
        accountMap.put(account1.getAccountNumber(), account1);
        accountMap.put(account2.getAccountNumber(), account2);
    }

    @Override
    public void saveTransaction(TransactionModel transactionModel) {
        if (Objects.isNull(transactionModel.getAccountNumber())) {
            throw new RuntimeException("You have to pass the account number!");
        } else {
            Account account = accountMap.get(transactionModel.getAccountNumber());
            if (Objects.isNull(accountMap.get(transactionModel.getAccountNumber()))) {
                throw new RuntimeException("Account number does not exist");
            } else {
                setBalance(transactionModel, account);
                countTransactions++;
                createReport();
            }
        }
    }

    private void createReport() {
        if (countTransactions % 10 == 0) {
            buildReport(reportMap);
        }
    }

    private void setBalance(TransactionModel transactionModel, Account account) {
        validateTransactionModel(transactionModel);
        if (transactionModel.getCurrency().equals(account.getCurrency())) {
            account.setBallance(account.getBallance() + transactionModel.getAmmount());
            addTransactionToReport(transactionModel);
        } else {
            account.setBallance(account.getBallance() + transactionModel.getAmmount() * transactionModel.getExchangeRate());
            addTransactionToReport(transactionModel);
        }
    }

    private void validateTransactionModel(TransactionModel transactionModel) {
        if (transactionModel.getAmmount() == 0) {
            throw new RuntimeException("Amount can not be 0");
        } else if (Objects.isNull(transactionModel.getExchangeRate())) {
            throw new RuntimeException("If currencies are not the same, Exchange rate can not be null");
        } else if (Objects.isNull(transactionModel.getCurrency()) || transactionModel.getCurrency().equals("")) {
            throw new RuntimeException("You have to pass currency");
        }
    }

    private Account findAccount(String accountNumber) {
        return accountMap.get(accountNumber);
    }

    private void addTransactionToReport(TransactionModel transactionModel) {
        if (!Objects.isNull(reportMap.get(transactionModel.getAccountNumber()))) {
            List<TransactionModel> transactionModelList = reportMap.get(transactionModel.getAccountNumber());
            transactionModelList.add(transactionModel);
            reportMap.put(transactionModel.getAccountNumber(), transactionModelList);
        } else {
            List<TransactionModel> transactionModelList = new ArrayList<>();
            transactionModelList.add(transactionModel);
            reportMap.put(transactionModel.getAccountNumber(), transactionModelList);
        }
    }

    private void buildReport(Map<String, List<TransactionModel>> reportMap) {
        for (Map.Entry<String, List<TransactionModel>> entry : reportMap.entrySet()) {
            String key = entry.getKey();
            List<TransactionModel> value = entry.getValue();
            Account account = findAccount(key);
            System.out.println("\n" + key + " - Egyenleg: " + account.getBallance() + " " + account.getCurrency());
            System.out.println(value.toString());
        }
    }
}
