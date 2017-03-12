package service;

import entity.Payment;
import org.springframework.stereotype.Service;

// Обслужить запрос на проведение платежа
public interface BankService {
    void processPayment(Payment payment);
}
