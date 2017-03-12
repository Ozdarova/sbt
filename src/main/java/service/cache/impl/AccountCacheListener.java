package service.cache.impl;

import entity.Account;
import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;

@Listener(clustered = true)
public class AccountCacheListener {

   @CacheEntryCreated
   public void entryCreated(CacheEntryCreatedEvent<Long, Account> event) {
      if (!event.isOriginLocal()) {
         System.out.printf("-- Entry for %s modified by another node in the cluster\n", event.getKey());
      }
   }

}
