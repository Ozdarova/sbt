import service.BankService;
import service.impl.SBTBankService;

import java.io.IOException;

public class BankApp {

    public static void main(String[] args) {
        String configPath = Thread.currentThread().getContextClassLoader()
                .getResource("configSample.yml").getPath();

        try {
            BankService bankService = SBTBankService.getInstance(configPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
