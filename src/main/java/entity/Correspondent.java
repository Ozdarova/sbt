package entity;

import org.infinispan.commons.marshall.Externalizer;
import org.infinispan.commons.marshall.SerializeWith;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

@SerializeWith(Correspondent.CorrespondentExternalizer.class)
public class Correspondent implements Serializable {
    private long accountId;
    private long bankId;
    private long correspondentAccountId;

    protected Correspondent(long accountId, long bankId, long correspondantAccountId) {
        this.accountId = accountId;
        this.bankId = bankId;
        this.correspondentAccountId = correspondantAccountId;
    }

    public long getBankId() {
        return bankId;
    }

    public long getAccountId() {
        return accountId;
    }

    public long getCorrespondentAccountId() {
        return correspondentAccountId;
    }

    public static class CorrespondentExternalizer implements Externalizer<Correspondent> {

        @Override
        public void writeObject(ObjectOutput objectOutput, Correspondent correspondent) throws IOException {
            objectOutput.writeLong(correspondent.getAccountId());
            objectOutput.writeLong(correspondent.getBankId());
            objectOutput.writeLong(correspondent.getCorrespondentAccountId());
        }

        @Override
        public Correspondent readObject(ObjectInput input) throws IOException, ClassNotFoundException {
            long accountId = input.readLong();
            long bankId = input.readLong();
            long correspondantAccountId = input.readLong();
            return new Correspondent(bankId, accountId, correspondantAccountId);
        }

    }

    public static class Builder {
        private long accountId;
        private long bankId;
        private long correspondentAccountId;

        public Builder setBankId(long bankId) {
            this.bankId = bankId;
            return this;
        }

        public Builder setAccountId(long accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder setCorrespondentAccountId(long correspondentAccountId) {
            this.correspondentAccountId = correspondentAccountId;
            return this;
        }

        public Correspondent build() {
            return new Correspondent(accountId, bankId, correspondentAccountId);
        }
    }
}
