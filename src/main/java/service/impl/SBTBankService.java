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

import javax.management.InstanceNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SBTBankService implements BankService, Runnable {

    public static BankService self;

    private BankInfoProvider bankInfoProvider;
    private AccountProvider accountProvider;
    private CorrespondentProvider correspondentProvider;
    private PaymentProvider paymentProvider;

    private BankInfo bankInfo;
    private final LinkedHashSet<Payment> payments = new LinkedHashSet<>();

    public static BankService getInstance(String configPath) throws IOException {
        if (self == null) {
            self = new SBTBankService(configPath);
        }
        return self;
    }

    //Ignore
    public static BankService getInstance() throws InstanceNotFoundException {
        if (self == null) {
            throw new InstanceNotFoundException("Please, create via getInstance(String configPath)!");
        }
        return self;
    }

    private SBTBankService(String configPath) throws IOException {
        this.bankInfoProvider = new BankInfoProviderImpl();
        this.accountProvider = new AccountProviderImpl();
        this.correspondentProvider = new CorrespondentProviderImpl();
        this.paymentProvider = new PaymentProviderImpl() {
            @Override
            public void passPayment(Payment payment) {
                self.processPayment(payment);
            }
        };

        try (InputStream in = new FileInputStream(configPath)) {
            BankConfig config = new Yaml().loadAs(in, BankConfig.class);
            readConfig(config);
        }
    }

    private void readConfig(BankConfig config) {
        this.bankInfo = new BankInfo.Builder()
                .setId(Long.parseLong(config.getInfo().getId()))
                .setName(config.getInfo().getName())
                .build();

        //@TODO: check if this bank already registered, throw exc

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
        if (!payments.contains(payment)) {
            payments.add(payment);
        }

        paymentProvider.remove(payment);
    }

    @Override
    public BankInfo getBankInfo() {
        return this.bankInfo;
    }

    @Override
    public List<Account> getAccounts() {
        return accountProvider.getAll().stream()
                .filter((a) -> a.getBankId() == this.bankInfo.getId())
                .collect(Collectors.toList());
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

    private boolean isValidOperaton(Account a1, Account a2, double value) {
        return a1.getBalance() - value >= 0 && a2.getBalance() + value >= 0;
    }

    @Override
    public void run() {
        Iterator<Payment> it = payments.iterator();

        while (it.hasNext()) {
            Payment p = it.next();

            Account fromAccount = accountProvider.getById(p.getFrom());
            Account toAccount = accountProvider.getById(p.getTo());

            if (isValidOperaton(fromAccount, toAccount, p.getValue())) {

                if (bankInfo.getId() == fromAccount.getBankId()
                        && bankInfo.getId() == toAccount.getBankId()) {
                    processLocalPayment(p);
                } else if (bankInfo.getId() != fromAccount.getBankId()
                        && bankInfo.getId() != toAccount.getBankId()) {
                    Payment payment = new Payment.Builder()
                            .setBankId(fromAccount.getBankId())
                            .setId(Long.parseLong(UUID.randomUUID().toString()))
                            .setFrom(p.getFrom())
                            .setTo(p.getTo())
                            .setValue(p.getValue())
                            .build();

                    paymentProvider.put(payment);
                } else {
                    Account fromCor = accountProvider.getById
                            (correspondentProvider.getByAccountId(toAccount.getId()).getCorrespondentAccountId());
                    Account toCor = accountProvider.getById
                            (correspondentProvider.getByAccountId(fromAccount.getId()).getCorrespondentAccountId());
                    if (bankInfo.getId() == fromAccount.getBankId()) {
                        if ( isValidOperaton(fromAccount, fromCor, p.getValue()) ) {
                            //@TODO: continue logics
                        }
                    } else {

                    }
                }
            }

            payments.remove(p);
        }
    }
}
