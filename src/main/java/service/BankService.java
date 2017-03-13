package service;

import entity.Account;
import entity.Payment;
import org.springframework.stereotype.Service;

import java.util.List;

// Обслужить запрос на проведение платежа
public interface BankService {
    void processPayment(Payment payment);
    List<Account> getAccounts();
}
