package service.configuration;

import java.util.List;

public class BankConfig {
    private InfoConfig infoConfig;
    private List<AccountConfig> accountConfigList;
    private List<CorrespondentConfig> correspondentConfigList;

    public BankConfig(InfoConfig infoConfig, List<AccountConfig> accountConfigList, List<CorrespondentConfig> correspondentConfigList) {
        this.infoConfig = infoConfig;
        this.accountConfigList = accountConfigList;
        this.correspondentConfigList = correspondentConfigList;
    }

    public InfoConfig getInfoConfig() {
        return infoConfig;
    }

    public void setInfoConfig(InfoConfig infoConfig) {
        this.infoConfig = infoConfig;
    }

    public List<AccountConfig> getAccountConfigList() {
        return accountConfigList;
    }

    public void setAccountConfigList(List<AccountConfig> accountConfigList) {
        this.accountConfigList = accountConfigList;
    }

    public List<CorrespondentConfig> getCorrespondentConfigList() {
        return correspondentConfigList;
    }

    public void setCorrespondentConfigList(List<CorrespondentConfig> correspondentConfigList) {
        this.correspondentConfigList = correspondentConfigList;
    }
}
