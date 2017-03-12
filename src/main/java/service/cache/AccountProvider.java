package service.cache;

import entity.Account;

import java.util.List;

public interface AccountProvider {
    Account getById(long id);
    List<Account> getAll();
    void put(Account account);
}
