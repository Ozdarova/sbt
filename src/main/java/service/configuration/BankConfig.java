package service.configuration;

import java.util.List;

public class BankConfig {

    private InfoConfig info;
    private List<AccountConfig> accounts;
    private List<CorrespondentConfig> correspondents;

    public BankConfig() {
    }

    public InfoConfig getInfo() {
        return info;
    }

    public void setInfo(InfoConfig info) {
        this.info = info;
    }

    public List<AccountConfig> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountConfig> accounts) {
        this.accounts = accounts;
    }

    public List<CorrespondentConfig> getCorrespondents() {
        return correspondents;
    }

    public void setCorrespondents(List<CorrespondentConfig> correspondents) {
        this.correspondents = correspondents;
    }
}
