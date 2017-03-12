package service.cache.impl;

import entity.Payment;
import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;

@Listener(clustered = true)
public class PaymentCacheListener {

    @CacheEntryCreated
    public void entryCreated(CacheEntryCreatedEvent<Long, Payment> event) {
        if (!event.isOriginLocal()) {
            System.out.printf("-- Entry for %s modified by another node in the cluster\n", event.getKey());
        }
    }

}
