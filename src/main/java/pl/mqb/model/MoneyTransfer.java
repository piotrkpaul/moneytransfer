package pl.mqb.model;

import java.math.BigDecimal;

final public class MoneyTransfer {

    private final String source;
    private final String target;
    private final BigDecimal amount;

    public MoneyTransfer(String source, String target, String amount) {
        this.source = source;
        this.target = target;
        this.amount = new BigDecimal(amount);
    }

    public MoneyTransfer() {
        //default constructor added for Jackson compatibility
        source = "";
        target = "";
        amount = BigDecimal.ZERO;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "MoneyTransfer{" +
                "sourceAccountId='" + source + '\'' +
                ", targetAccountId='" + target + '\'' +
                ", amount=" + amount +
                '}';
    }
}
