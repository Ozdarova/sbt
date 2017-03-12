package service.cache;

import entity.Correspondant;
import org.springframework.stereotype.Service;

@Service
public interface CorrespondantProvider {
    Correspondant getByBankId(long bankId);
}
