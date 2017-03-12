package service.configuration;

public class CorrespondentConfig {
    private String accountId;
    private String bankId;
    private String correspondentAccountId;

    public CorrespondentConfig(){
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getCorrespondentAccountId() {
        return correspondentAccountId;
    }

    public void setCorrespondentAccountId(String correspondentAccountId) {
        this.correspondentAccountId = correspondentAccountId;
    }
}
