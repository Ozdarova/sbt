package service.impl;

import entity.Account;
import entity.BankInfo;
import entity.Payment;
import service.BankService;
import service.cache.AccountProvider;
import service.cache.BankInfoProvider;
import service.cache.CorrespondentProvider;

public class SBTBankService implements BankService {

    private AccountProvider accountProvider;

    private BankInfoProvider bankInfoProvider;

    private CorrespondentProvider correspondentProvider;

    private BankInfo bankInfo;

    public SBTBankService (String configPath) {
        // создать bankinfo, аккаунты в кеш
    }

    @Override
    public void processPayment(Payment payment) {
        Account fromAccount = accountProvider.getById(payment.getFrom());
        Account toAccount = accountProvider.getById(payment.getTo());

    }

    public void processLocalPayment(Payment payment) {
        Account fromAccount = accountProvider.getById(payment.getFrom());
        Account toAccount = accountProvider.getById(payment.getTo());

        double fromForecast = fromAccount.getBalance() - payment.getValue();
        double toForecast = toAccount.getBalance() + payment.getValue();

        fromAccount.setBalance(fromForecast);
        toAccount.setBalance(toForecast);

        accountProvider.put(fromAccount);
        accountProvider.put(toAccount);
    }
//
    private boolean isValidOperaton(Account a1, Account a2, double value) {
        return a1.getBalance() - value >= 0 && a2.getBalance() + value >= 0;
    }
}
