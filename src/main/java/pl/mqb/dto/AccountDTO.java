package pl.mqb.dto;

import pl.mqb.model.Account;

public class AccountDTO {

    private final String id;
    private final String balance;

    private AccountDTO(String id, String balance) {
        this.id = id;
        this.balance = balance;
    }

    public static AccountDTO from(Account account) {
        return new AccountDTO(account.getId(), String.valueOf(account.getBalance()));
    }

    public String getId() {
        return id;
    }

    public String getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id='" + id + '\'' +
                ", balance='" + balance + '\'' +
                '}';
    }
}