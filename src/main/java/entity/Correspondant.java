package entity;

import org.infinispan.commons.marshall.Externalizer;
import org.infinispan.commons.marshall.SerializeWith;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

@SerializeWith(Correspondant.CorrespondantExternalizer.class)
public class Correspondant implements Serializable {
    private long bankId;
    private long accountId;
    private long correspondantAccountId;

    protected Correspondant(long bankId, long accountId, long correspondantAccountId) {
        this.bankId = bankId;
        this.accountId = accountId;
        this.correspondantAccountId = correspondantAccountId;
    }

    public long getBankId() {
        return bankId;
    }

    public long getAccountId() {
        return accountId;
    }

    public long getCorrespondantAccountId() {
        return correspondantAccountId;
    }

    public static class CorrespondantExternalizer implements Externalizer<Correspondant> {

        @Override
        public void writeObject(ObjectOutput objectOutput, Correspondant correspondant) throws IOException {
            objectOutput.writeLong(correspondant.getBankId());
            objectOutput.writeLong(correspondant.getAccountId());
            objectOutput.writeLong(correspondant.getCorrespondantAccountId());
        }

        @Override
        public Correspondant readObject(ObjectInput input) throws IOException, ClassNotFoundException {
            long id = input.readLong();
            long accountId = input.readLong();
            long correspondantAccountId = input.readLong();
            return new Correspondant(id, accountId, correspondantAccountId);
        }

    }

    public static class Builder {
        private long bankId;
        private long accountId;
        private long correspondantAccountId;

        public Builder setBankId(long bankId) {
            this.bankId = bankId;
            return this;
        }

        public Builder setAccountId(long accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder setCorrespondantAccountId(long correspondantAccountId) {
            this.correspondantAccountId = correspondantAccountId;
            return this;
        }

        public Correspondant build() {
            return new Correspondant(bankId, accountId, correspondantAccountId);
        }
    }
}
