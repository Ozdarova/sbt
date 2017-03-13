package service.cache.impl;

import entity.BankInfo;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.cache.BankInfoProvider;

import javax.transaction.TransactionManager;

public class BankInfoProviderImpl implements BankInfoProvider {
    final private Logger logger = LoggerFactory.getLogger(BankInfoProvider.class);

    final private Cache<Long, BankInfo> cache;

    public BankInfoProviderImpl() {
        GlobalConfigurationBuilder global = GlobalConfigurationBuilder.defaultClusteredBuilder();
        global
                .globalJmxStatistics().allowDuplicateDomains(true)
                .transport().clusterName("BankInfoService");


        ConfigurationBuilder config = new ConfigurationBuilder();
        config.clustering().cacheMode(CacheMode.DIST_SYNC);

        EmbeddedCacheManager cacheManager = new DefaultCacheManager(global.build(), config.build());
        cache = cacheManager.getCache();
    }

    @Override
    public BankInfo getById(long id) {
        return cache.get(id);
    }

    @Override
    public void put(BankInfo bankInfo) {
        TransactionManager manager = cache.getAdvancedCache().getTransactionManager();
        try {
            manager.begin();
            if (!cache.containsKey(bankInfo.getId())) {
                cache.put(bankInfo.getId(), bankInfo);
            } else {
                cache.replace(bankInfo.getId(), bankInfo);
            }
        } catch (Exception e) {
            logger.error("Error inserting BankInfo: " + e.getMessage(), e);
        }
    }
}
