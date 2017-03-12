package service.cache;

import entity.BankInfo;
import org.springframework.stereotype.Service;

@Service
public interface BankInfoProvider {
    BankInfo getById(long id);
}
