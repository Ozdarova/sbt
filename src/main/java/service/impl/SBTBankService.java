package service.impl;

import entity.BankInfo;
import entity.Payment;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.distribution.group.Grouper;
import service.BankService;
import service.cache.AccountProvider;
import service.cache.BankInfoProvider;
import service.cache.CorrespondantProvider;
import service.cache.DocumentResolver;

public class SBTBankService implements BankService {

    private AccountProvider accountProvider;

    BankInfoProvider bankInfoProvider;

    CorrespondantProvider correspondantProvider;

    DocumentResolver documentResolver;

    private BankInfo bankInfo;

    public SBTBankService (String configPath) {
        // создать bankinfo, аккаунты в кеш
        GlobalConfigurationBuilder global = GlobalConfigurationBuilder.defaultClusteredBuilder();
        global.transport().clusterName("BankService");
        ConfigurationBuilder config = new ConfigurationBuilder();
        config
                .clustering().cacheMode(CacheMode.DIST_SYNC)
                .hash().groups().enabled().addGrouper(new SBTBankService.BankGrouper());
    }

    @Override
    public void processPayment(Payment payment) {
//        PaymentDocument paymentDocument = new PaymentDocument.Builder()
//                .setBankId(publicInfoService.getBankInfo().getId())
//                .setPayment(payment)
//                .setFilePath("filepath")
//                .build();
//        documentResolver.writePaymentDocument(paymentDocument);
//
//        Account fromAccount = publicInfoService.getAccount(payment.getFrom());
//        Account toAccount = publicInfoService.getAccount(payment.getTo());

    }

    public class BankGrouper implements Grouper<Long> {
        @Override
        public String computeGroup(Long key, String group) {
            return String.valueOf(key);
        }

        @Override
        public Class<Long> getKeyType() {
            return Long.class;
        }
    }

//    public void processLocalPayment(Payment payment) {
//        Account fromAccount = publicInfoService.getAccount(payment.getFrom());
//        Account toAccount = publicInfoService.getAccount(payment.getTo());
//
//        double fromForecast = fromAccount.getBalance() - payment.getValue();
//        double toForecast = toAccount.getBalance() + payment.getValue();
//
//        fromAccount.setBalance(fromForecast);
//        toAccount.setBalance(toForecast);
//    }
//
//    private boolean isValidOperaton(Account a1, Account a2, double value) {
//        return a1.getBalance() - value >= 0 && a2.getBalance() + value >= 0;
//    }
}
