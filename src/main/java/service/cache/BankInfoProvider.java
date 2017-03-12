package service.cache;

import entity.BankInfo;

public interface BankInfoProvider {
    BankInfo getById(long id);
    void put(BankInfo bankInfo);
}
