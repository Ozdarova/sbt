package service.cache.impl;

import entity.Account;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import service.cache.AccountProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountProviderImpl implements AccountProvider {
    final private Cache<Long, Account> cache;

    public AccountProviderImpl() {
        GlobalConfigurationBuilder global = GlobalConfigurationBuilder.defaultClusteredBuilder();
        global.transport().clusterName("AccountService");

        ConfigurationBuilder config = new ConfigurationBuilder();
        config.clustering().cacheMode(CacheMode.DIST_SYNC);

        EmbeddedCacheManager cacheManager = new DefaultCacheManager(global.build(), config.build());
        cache = cacheManager.getCache();
        cache.addListener(new AccountCacheListener());
    }

    @Override
    public Account getById(long id) {
        return cache.get(id);
    }

    @Override
    public List<Account> getAll() {
        return cache.size() != 0
                ? cache.values().stream().collect(Collectors.toList())
                : new ArrayList<>() ;
    }

    @Override
    public void put(Account account) {
        if (!cache.containsKey(account.getId())) {
            System.out.println("this is new");
            cache.put(account.getId(), account);
        } else {
            System.out.println("this is old");
            cache.replace(account.getId(), account);
        }
    }
}
