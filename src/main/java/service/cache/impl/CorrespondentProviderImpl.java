package service.cache.impl;

import entity.Correspondent;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.cache.CorrespondentProvider;

import javax.transaction.TransactionManager;

public class CorrespondentProviderImpl implements CorrespondentProvider {
    final private Logger logger = LoggerFactory.getLogger(CorrespondentProvider.class);

    final private Cache<Long, Correspondent> cache;

    public CorrespondentProviderImpl() {
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
    public Correspondent getByAccountId(long accountId) {
        return cache.get(accountId);
    }

    @Override
    public void put(Correspondent correspondent) {
        TransactionManager manager = cache.getAdvancedCache().getTransactionManager();
        try {
            manager.begin();

            if (!cache.containsKey(correspondent.getAccountId())) {
                cache.put(correspondent.getAccountId(), correspondent);
            } else {
                cache.replace(correspondent.getAccountId(), correspondent);
            }

            manager.commit();
        } catch (Exception e) {
            logger.error("Error inserting Correspondent: " + e.getMessage(), e);
        }
    }
}
