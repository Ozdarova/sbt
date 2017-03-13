package service.cache.impl;

import entity.Payment;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.BankService;
import service.cache.PaymentProvider;
import service.cache.listeners.PaymentCacheListener;

import javax.transaction.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class PaymentProviderImpl implements PaymentProvider {
    final private Logger logger = LoggerFactory.getLogger(PaymentProvider.class);

    final private Cache<Long, Payment> cache;

    final private BankService client;

    public PaymentProviderImpl(BankService client) {
        this.client = client;

        GlobalConfigurationBuilder global = GlobalConfigurationBuilder.defaultClusteredBuilder();
        global
                .globalJmxStatistics().allowDuplicateDomains(true)
                .transport().clusterName("PaymentService");


        ConfigurationBuilder config = new ConfigurationBuilder();
        config.clustering().cacheMode(CacheMode.DIST_SYNC);

        EmbeddedCacheManager cacheManager = new DefaultCacheManager(global.build(), config.build());
        cache = cacheManager.getCache();

        PaymentCacheListener listener = new PaymentCacheListener() {
            @Override
            public boolean isClientPayment(Payment payment) {
                return client.getBankInfo().getId() == payment.getBankId();
            }

            @Override
            public void passPayment(Payment payment) {
                client.processPayment(payment);
            }
        };
        cache.addListener(listener);
    }

    public abstract void passPayment(Payment payment);

    @Override
    public List<Payment> getAll() {
        return cache.size() != 0
                ? cache.values().stream().collect(Collectors.toList())
                : new ArrayList<>();
    }

    @Override
    public void put(Payment payment) {
        TransactionManager manager = cache.getAdvancedCache().getTransactionManager();
        try {
            manager.begin();

            if (!cache.containsKey(payment.getId())) {
                cache.put(payment.getId(), payment);
            } else {
                cache.replace(payment.getId(), payment);
            }

            manager.commit();
        } catch (Exception e) {
            logger.error("Error inserting Payment: " + e.getMessage(), e);
        }

    }

    @Override
    public void remove(Payment payment) {
        if ( cache.containsKey(payment.getId()) ) {
            TransactionManager manager = cache.getAdvancedCache().getTransactionManager();
            try {
                manager.begin();
                cache.remove(payment);
                manager.commit();
            } catch (Exception e) {
                logger.error("Error removing Payment: " + e.getMessage(), e);
            }
        }
    }
}
