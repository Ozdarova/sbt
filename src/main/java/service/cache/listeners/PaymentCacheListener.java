package service.cache.listeners;

import entity.Payment;
import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;

@Listener(clustered = true)
public abstract class PaymentCacheListener {

    @CacheEntryCreated
    public void entryCreated(CacheEntryCreatedEvent<Long, Payment> event) {
        if (!event.isOriginLocal()) {
            System.out.printf("-- Entry for %s modified by another node in the cluster\n", event.getKey());
        }
    }

    abstract void passPayment(Payment payment);

}
