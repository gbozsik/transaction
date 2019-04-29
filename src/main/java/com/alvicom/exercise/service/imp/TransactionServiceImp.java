package com.alvicom.exercise.service.imp;

import com.alvicom.exercise.domain.Account;
import com.alvicom.exercise.domain.model.TransactionModel;
import com.alvicom.exercise.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
public class TransactionServiceImp implements TransactionService {

    private Account account;
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
    public TransactionServiceImp(Account account) {
        this.account = account;
    }

    @Override
    public void saveTransaction(TransactionModel transactionModel) {
        if (Objects.isNull(transactionModel.getAccountNumber())) {
            System.out.println("You have to pass the account number!");
        } else {
            Account account = findAccount(transactionModel.getAccountNumber());
            if (Objects.isNull(account)) {
                System.out.println("Account number does not exist");
            } else {
                setBalance(transactionModel, account);
                countTransactions++;
                if (countTransactions % 10 == 0) {
                    buildReport(reportMap);
                }
            }
        }
    }

    private void setBalance(TransactionModel transactionModel, Account account) {
        if (transactionModel.getAmmount() == 0) {
            System.out.println("Amount can not be 0");
        } else if (exchangeRateIsNull(transactionModel)) {
            System.out.println("If currencies are not the same, Exchange rate can not be null");
        } else if (Objects.isNull(transactionModel.getCurrency()) || transactionModel.getCurrency() == "") {
            System.out.println("You have to pass currency");
        } else if (currencyIsEqual(account.getCurrency(), transactionModel.getCurrency())) {
            account.setBallance(account.getBallance() + transactionModel.getAmmount());
            addTransactionToReport(transactionModel, account);
        }  else {
            account.setBallance(account.getBallance() + transactionModel.getAmmount() * transactionModel.getAxchangeRate());
            addTransactionToReport(transactionModel, account);
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

    private boolean currencyIsEqual(String accountCurrency, String transactionCurrency) {
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
        } else {
            List<TransactionModel> transactionModelList = new ArrayList<>();
            transactionModelList.add(transactionModel);
            reportMap.put(transactionModel.getAccountNumber(), transactionModelList);
        }
    }

    private List<Map<String, List<TransactionModel>>> buildReport(Map<String, List<TransactionModel>> reportMap) {
        List<Map<String, List<TransactionModel>>> mapList = new ArrayList<>();
        for (Map.Entry<String, List<TransactionModel>> entry : reportMap.entrySet()) {
            String key = entry.getKey();
            List<TransactionModel> value = entry.getValue();
            Map<String, List<TransactionModel>> modifieadMap = new HashMap<>();
            Account account = findAccount(key);
            key += " - Egyenleg: " + account.getBallance() + " " + account.getCurrency();
            modifieadMap.put(key, value);
            System.out.println("\n" + key);
            System.out.println(value.toString());
            mapList.add(modifieadMap);
        }
        return mapList;
    }
}
