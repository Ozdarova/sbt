package service.cache;

import entity.Payment;

import java.util.List;

public interface PaymentProvider {
    List<Payment> getAll();
    void put(Payment payment);
    void remove(Payment payment);
}
