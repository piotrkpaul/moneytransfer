package model;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@XmlRootElement
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
        balance = balance.subtract(amount);
        return balance;
    }

    public BigDecimal credit(BigDecimal amount) {
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
