package service.cache;

import entity.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountProvider {
    Account getById(long id);
    List<Account> getAll();
    void put(Account account);
}
