import service.cache.AccountProvider;
import service.impl.SBTBankService;

import java.io.IOException;

public class BankApp {

    static AccountProvider accountProvider;

    public static void main(String[] args) {
        String configPath = Thread.currentThread().getContextClassLoader()
                .getResource("configSample.yml").getPath();

        try {
            SBTBankService bankService = new SBTBankService(configPath);
            System.out.println(bankService.getall().size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
