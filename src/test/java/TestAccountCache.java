import entity.Account;
import entity.BankInfo;
import entity.Correspondent;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;
import service.configuration.BankConfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class TestAccountCache {
    @Test
    public void test() {
        try (InputStream in = new FileInputStream(
                Thread.currentThread().getContextClassLoader().getResource("configSample.yml").getPath())) {
            BankConfig config = new Yaml().loadAs(in, BankConfig.class);

            System.out.println("loaded");

            BankInfo bi = new BankInfo.Builder()
                    .setId(Long.parseLong(config.getInfo().getId()))
                    .setName(config.getInfo().getName())
                    .build();

            List<Account> accounts = config.getAccounts().stream()
                    .map((ai) -> new Account.Builder()
                            .setId(Long.parseLong(ai.getId()))
                            .setBalance(Double.parseDouble(ai.getBalance()))
                            .build())
                    .collect(Collectors.toList());

            List<Correspondent> correspondents = config.getCorrespondents().stream()
                    .map((c) -> new Correspondent.Builder()
                            .setAccountId(Long.parseLong(c.getAccountId()))
                            .setBankId(Long.parseLong(c.getBankId()))
                            .setCorrespondentAccountId(Long.parseLong(c.getCorrespondentAccountId()))
                            .build())
                    .collect(Collectors.toList());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
