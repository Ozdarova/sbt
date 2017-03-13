package service.cache.impl;

import entity.Account;
import org.infinispan.AdvancedCache;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.cache.AccountProvider;
import service.cache.BankInfoProvider;

import javax.transaction.TransactionManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class AccountProviderImpl implements AccountProvider {
    final private Logger logger = LoggerFactory.getLogger(AccountProvider.class);

    final private Cache<Long, Account> cache;

    public AccountProviderImpl() {
        GlobalConfigurationBuilder global = GlobalConfigurationBuilder.defaultClusteredBuilder();
        global
                .globalJmxStatistics().allowDuplicateDomains(true)
                .transport().clusterName("AccountService");

        ConfigurationBuilder config = new ConfigurationBuilder();
        config.clustering().cacheMode(CacheMode.DIST_SYNC);

        EmbeddedCacheManager cacheManager = new DefaultCacheManager(global.build(), config.build());
        cache = cacheManager.getCache();

    }

    @Override
    public Account getById(long id) {
        return cache.get(id);
    }

    @Override
    public List<Account> getAll() {
        return cache.size() != 0
                ? cache.values().stream().collect(Collectors.toList())
                : new ArrayList<>();
    }

    @Override
    public void put(Account account) {
        TransactionManager manager = cache.getAdvancedCache().getTransactionManager();
        try {
            manager.begin();
            if (!cache.containsKey(account.getId())) {
                cache.put(account.getId(), account);
            } else {
                cache.replace(account.getId(), account);
            }
        } catch (Exception e) {
            logger.error("Error inserting Account: " + e.getMessage(), e);
        }
    }
}
