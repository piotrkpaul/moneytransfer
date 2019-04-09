package pl.mqb.model;

import pl.mqb.error.InsufficientBalanceException;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Account {

    private final String id;
    private BigDecimal balance;

    public Account() {
        this.id = UUID.randomUUID().toString();
        this.balance = BigDecimal.ZERO;
    }

    public Account(String balance) {
        this.id = UUID.randomUUID().toString();
        this.balance = new BigDecimal(balance);
    }

    public Account(String id, String balance) {
        this.id = id;
        this.balance = new BigDecimal(balance);
    }

    public String getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal debit(BigDecimal amount) {
        validate(amount);

        if (balance.compareTo(amount) < 0)
            throw new InsufficientBalanceException("Debit can't be performed due to lack of funds on account.");

        balance = balance.subtract(amount);
        return balance;
    }

    private void validate(BigDecimal amount) {
        if (Objects.isNull(amount) || BigDecimal.ZERO.compareTo(amount) > 0) {
            throw new IllegalArgumentException("You can only credit positive amount.");
        }
    }

    public BigDecimal credit(BigDecimal amount) {
        validate(amount);

        balance = balance.add(amount);
        return balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
