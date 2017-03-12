package service.cache;

import entity.Correspondent;

public interface CorrespondentProvider {
    Correspondent getByAccountId(long accountId);
    void put(Correspondent correspondent);
}
