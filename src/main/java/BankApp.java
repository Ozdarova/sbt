import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.BankService;
import service.impl.SBTBankService;

import java.io.IOException;

public class BankApp {

    private final static Logger logger = LoggerFactory.getLogger(BankApp.class);

    public static void main(String[] args) {
        String configPath = Thread.currentThread().getContextClassLoader()
                .getResource("configSample.yml").getPath();
        try {
            BankService bankService = SBTBankService.getInstance(configPath);
        } catch (IOException e) {
            logger.error(" Failed to start BankService: " + e, e.getMessage());
            e.printStackTrace();
        }
    }

}
