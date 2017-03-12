package entity;

import org.infinispan.commons.marshall.Externalizer;
import org.infinispan.commons.marshall.SerializeWith;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

@SerializeWith(BankInfo.BankInfoExternalizer.class)
public class BankInfo implements Serializable {
    private long id;
    private String name;

    protected BankInfo(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static class BankInfoExternalizer implements Externalizer<BankInfo> {

        @Override
        public void writeObject(ObjectOutput objectOutput, BankInfo bankInfo) throws IOException {
            objectOutput.writeLong(bankInfo.getId());
            objectOutput.writeUTF(bankInfo.getName());
        }

        @Override
        public BankInfo readObject(ObjectInput input) throws IOException, ClassNotFoundException {
            long id = input.readLong();
            String name = input.readUTF();
            return new BankInfo(id, name);
        }

    }

    public static class Builder {
        private long id;
        private String name;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public BankInfo build() {
            return new BankInfo(id, name);
        }
    }
}
