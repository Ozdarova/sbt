package service.cache;

import entity.Correspondent;

public interface CorrespondentProvider {
    Correspondent getByBankId(long bankId);
}
