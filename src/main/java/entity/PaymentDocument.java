package entity;

public class PaymentDocument {
    private long bankId;
    private String filePath;
    private Payment payment;

    public PaymentDocument(long bankId, String filePath, Payment payment) {
        this.bankId = bankId;
        this.filePath = filePath;
        this.payment = payment;
    }

    public long getBankId() {
        return bankId;
    }

    public String getFilePath() {
        return filePath;
    }

    public Payment getPayment() {
        return payment;
    }

    public static class Builder{
        private long bankId;
        private String filePath;
        private Payment payment;

        public Builder setBankId(long bankId) {
            this.bankId = bankId;
            return this;
        }

        public Builder setFilePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public Builder setPayment(Payment payment) {
            this.payment = payment;
            return this;
        }

        public PaymentDocument build() {
            //@TODO: validate in all builders
//            BuilderValidator.validateEntityFields(this);
            return new PaymentDocument(bankId, filePath, payment);
        }
    }
}
