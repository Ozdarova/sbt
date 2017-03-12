import entity.Account;
import service.cache.AccountProvider;
import service.cache.impl.AccountProviderImpl;

import java.util.Random;

public class BankApp {

    static AccountProvider accountProvider;

    public static void main(String[] args) {
        accountProvider = new AccountProviderImpl();
        System.out.println(accountProvider.getAll().size() + " accounts found!");
        Random rnd = new Random();

        accountProvider.put(new Account.Builder().setId(rnd.nextLong()).setBalance(100).build());
        System.out.println("Account created");


        System.out.println(accountProvider.getAll().size() + " accounts found!");
    }

}
