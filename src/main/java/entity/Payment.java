package entity;

import org.infinispan.commons.marshall.Externalizer;
import org.infinispan.commons.marshall.SerializeWith;
import org.infinispan.distribution.group.Grouper;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

@SerializeWith(Payment.PaymentExternalizer.class)
public class Payment implements Serializable {

    private long id;
    private long bankId;
    private long from;
    private long to;
    private double value;

    protected Payment(long id, long bankId, long from, long to, double value) {
        this.id = id;
        this.bankId = bankId;
        this.from = from;
        this.to = to;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public long getBankId() {
        return bankId;
    }

    public long getFrom() {
        return from;
    }

    public long getTo() {
        return to;
    }

    public double getValue() {
        return value;
    }

    public static class PaymentExternalizer implements Externalizer<Payment> {

        @Override
        public void writeObject(ObjectOutput objectOutput, Payment payment) throws IOException {
            objectOutput.writeLong(payment.getId());
            objectOutput.writeLong(payment.getBankId());
            objectOutput.writeLong(payment.getFrom());
            objectOutput.writeLong(payment.getTo());
            objectOutput.writeDouble(payment.getValue());
        }

        @Override
        public Payment readObject(ObjectInput input) throws IOException, ClassNotFoundException {
            long id = input.readLong();
            long bankId = input.readLong();
            long from = input.readLong();
            long to = input.readLong();
            double value = input.readDouble();
            return new Payment(id, bankId, from, to, value);
        }

    }

    public static class Builder {
        private long id;
        private long bankId;
        private long from;
        private long to;
        double value;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setBankId(long bankId) {
            this.bankId = bankId;
            return this;
        }

        public Builder setFrom(long from) {
            this.from = from;
            return this;
        }

        public Builder setTo(long to) {
            this.to = to;
            return this;
        }

        public Builder setValue(double value) {
            this.value = value;
            return this;
        }

        public Payment build() {
            return new Payment(id, bankId, from, to, value);
        }
    }

}
