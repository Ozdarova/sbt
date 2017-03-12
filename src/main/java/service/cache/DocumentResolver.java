package service.cache;

import entity.PaymentDocument;
import org.springframework.stereotype.Service;

// Чтение и создание платежного документа
@Service
public interface DocumentResolver {
    PaymentDocument readPaymentDocument(String path);
    void writePaymentDocument(PaymentDocument paymentDocument);
}
