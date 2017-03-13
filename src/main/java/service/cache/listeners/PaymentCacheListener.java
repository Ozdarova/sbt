package service.cache.listeners;

import entity.Payment;
import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;

@Listener(clustered = true)
public abstract class PaymentCacheListener {

    @CacheEntryCreated
    public void entryCreated(CacheEntryCreatedEvent<Long, Payment> event) {
        Payment payment = event.getValue();
        if ( isClientPayment(payment) )
            passPayment(payment);

    }

    public abstract boolean isClientPayment(Payment payment);
    public abstract void passPayment(Payment payment);

}
