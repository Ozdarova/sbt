package entity;

import org.infinispan.commons.marshall.Externalizer;
import org.infinispan.commons.marshall.SerializeWith;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

@SerializeWith(Account.AccountExternalizer.class)
public class Account implements Serializable {

    private long bankId;
    private long id;
    private double balance;

    protected Account(long bankId, long id, double balance) {
        this.bankId = bankId;
        this.id = id;
        this.balance = balance;
    }

    public long getBankId() {
        return bankId;
    }

    public long getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public static class AccountExternalizer implements Externalizer<Account> {

        @Override
        public void writeObject(ObjectOutput objectOutput, Account account) throws IOException {
            objectOutput.writeLong(account.getId());
            objectOutput.writeLong(account.getId());
            objectOutput.writeDouble(account.getBalance());
        }

        @Override
        public Account readObject(ObjectInput input) throws IOException, ClassNotFoundException {
            long bankId = input.readLong();
            long id = input.readLong();
            double balance = input.readDouble();
            return new Account(bankId, id, balance);
        }

    }

    public static class Builder {
        private long bankId;
        private long id;
        private double balance;

        public Builder setBankId(long bankId) {
            this.bankId = bankId;
            return this;
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setBalance(double balance) {
            this.balance = balance;
            return this;
        }

        public Account build() {
            return new Account(bankId, id, balance);
        }
    }
}
