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
            //@TODO: check if in local bank group
            passPayment(event.getValue());
        }
    }

    public abstract void passPayment(Payment payment);

}
