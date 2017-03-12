package service.cache.impl;

import entity.Correspondent;
import entity.Payment;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import service.cache.PaymentProvider;

import java.util.List;

public class PaymentProviderImpl implements PaymentProvider {
    final private Cache<Long, Payment> cache;

    public PaymentProviderImpl() {
        GlobalConfigurationBuilder global = GlobalConfigurationBuilder.defaultClusteredBuilder();
        global
                .globalJmxStatistics().allowDuplicateDomains(true)
                .transport().clusterName("CorrespondentService");


        ConfigurationBuilder config = new ConfigurationBuilder();
        config.clustering().cacheMode(CacheMode.DIST_SYNC);

        EmbeddedCacheManager cacheManager = new DefaultCacheManager(global.build(), config.build());
        cache = cacheManager.getCache();
    }

    @Override
    public List<Payment> getAll() {
        return null;
    }

    @Override
    public void put(Payment payment) {

    }
}
