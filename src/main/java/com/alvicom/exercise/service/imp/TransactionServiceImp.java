package com.alvicom.exercise.service.imp;

import com.alvicom.exercise.domain.Account;
import com.alvicom.exercise.domain.model.ReportModel;
import com.alvicom.exercise.domain.model.TransactionModel;
import com.alvicom.exercise.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class TransactionServiceImp implements TransactionService {

    private Account account;
    private ReportModel reportModel;
    private static int countTransactions;
    private Map<String, List<TransactionModel>> reportMap = new HashMap<>();
    private List<Account> accountList = new ArrayList<>();

    @PostConstruct
    private void fillAccountList() {
        Account account1 = new Account("11111111-22222222", "HUF", 150000);
        Account account2 = new Account("22222222-33333333", "USD", 1230);
        accountList.add(account1);
        accountList.add(account2);
    }

    @Autowired
    public TransactionServiceImp(Account account, ReportModel reportModel) {
        this.account = account;
        this.reportModel = reportModel;
    }

    @Override
    public Map<String, List<TransactionModel>> saveTransaction(TransactionModel transactionModel) {
        Account account = findAccount(transactionModel.getAccountNumber());
        if (Objects.isNull(account)) {
            throw new RuntimeException("Account not found");
        }
        setBalance(transactionModel, account);
        addTransactionToReport(transactionModel, account);
        countTransactions++;
        if (countTransactions % 10 == 0) {
            return reportMap;
        }
        return null;
    }

    private void setBalance(TransactionModel transactionModel, Account account) {
        Integer balance = account.getBallance();
        if (currencyiSEqual(account.getCurrency(), transactionModel.getCurrency())) {
            account.setBallance(balance += transactionModel.getAmmount());
        } else {
            if (exchangeRateIsNull(transactionModel)) {
                throw new RuntimeException("If currencies are not the same, Exchange rate can not be null");
            }
            account.setBallance(balance += transactionModel.getAmmount() * transactionModel.getAxchangeRate());
        }
    }

    private Account findAccount(String accountNumber) {
        for (Account accountInList : accountList) {
            if (accountNumber.equals(accountInList.getAccountNumber())) {
                return accountInList;
            }
        }
        return null;
    }

    private boolean currencyiSEqual(String accountCurrency, String transactionCurrency) {
        if (accountCurrency.equals(transactionCurrency)) {
            return true;
        }
        return false;
    }

    private boolean exchangeRateIsNull(TransactionModel transactionModel) {
        return Objects.isNull(transactionModel.getAxchangeRate());
    }

    private void addTransactionToReport(TransactionModel transactionModel, Account account) {
        if (!Objects.isNull(reportMap.get(transactionModel.getAccountNumber()))) {
            List<TransactionModel> transactionModelList = reportMap.get(transactionModel.getAccountNumber());
            transactionModelList.add(transactionModel);
            reportMap.put(transactionModel.getAccountNumber(), transactionModelList);
        }else {
            List<TransactionModel> transactionModelList = new ArrayList<>();
            transactionModelList.add(transactionModel);
            reportMap.put(transactionModel.getAccountNumber(), transactionModelList);
        }
    }
}