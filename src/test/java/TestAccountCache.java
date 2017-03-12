import entity.Account;
import org.junit.jupiter.api.Test;
import service.cache.AccountProvider;
import service.cache.impl.AccountProviderImpl;

public class TestAccountCache {
    AccountProvider provider;

    @Test
    public void test() {
        provider = new AccountProviderImpl();
        provider.put(new Account.Builder().setId(1).setBalance(100).build());

        Account account = provider.getById(1);
        System.out.println(account.getId() + ":" + account.getBalance());

//        AccountProvider prov2 = new AccountProviderImpl();
//        Account acc2 = prov2.getById(1);
//
//        System.out.println(acc2.getId() + ":" + acc2.getBalance());
    }
}
