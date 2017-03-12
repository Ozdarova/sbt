package service.impl;

import entity.Account;
import entity.BankInfo;
import entity.Correspondent;
import entity.Payment;
import org.yaml.snakeyaml.Yaml;
import service.BankService;
import service.cache.AccountProvider;
import service.cache.BankInfoProvider;
import service.cache.CorrespondentProvider;
import service.cache.PaymentProvider;
import service.cache.impl.AccountProviderImpl;
import service.cache.impl.BankInfoProviderImpl;
import service.cache.impl.CorrespondentProviderImpl;
import service.cache.impl.PaymentProviderImpl;
import service.configuration.BankConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class SBTBankService implements BankService {

    private BankInfoProvider bankInfoProvider;
    private AccountProvider accountProvider;
    private CorrespondentProvider correspondentProvider;
    private PaymentProvider paymentProvider;

    private BankInfo bankInfo;

    public SBTBankService(String configPath) throws IOException {
        this.bankInfoProvider = new BankInfoProviderImpl();
        this.accountProvider = new AccountProviderImpl();
        this.correspondentProvider = new CorrespondentProviderImpl();
        this.paymentProvider = new PaymentProviderImpl();

        try (InputStream in = new FileInputStream(configPath)) {
            BankConfig config = new Yaml().loadAs(in, BankConfig.class);
            readConfig(config);
        }
    }

    public List<Account> getall() {
        return accountProvider.getAll();
    }

    private void readConfig(BankConfig config) {
        this.bankInfo = new BankInfo.Builder()
                .setId(Long.parseLong(config.getInfo().getId()))
                .setName(config.getInfo().getName())
                .build();

        //@TODO: check if this bank already exists, throw exc

        List<Account> accounts = config.getAccounts().stream()
                .map((ai) -> new Account.Builder()
                        .setBankId(bankInfo.getId())
                        .setId(Long.parseLong(ai.getId()))
                        .setBalance(Double.parseDouble(ai.getBalance()))
                        .build())
                .collect(Collectors.toList());

        List<Correspondent> correspondents = config.getCorrespondents().stream()
                .map((c) -> new Correspondent.Builder()
                        .setAccountId(Long.parseLong(c.getAccountId()))
                        .setBankId(Long.parseLong(c.getBankId()))
                        .setCorrespondentAccountId(Long.parseLong(c.getCorrespondentAccountId()))
                        .build())
                .collect(Collectors.toList());

        bankInfoProvider.put(bankInfo);
        accounts.forEach(accountProvider::put);
        correspondents.forEach(correspondentProvider::put);
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
