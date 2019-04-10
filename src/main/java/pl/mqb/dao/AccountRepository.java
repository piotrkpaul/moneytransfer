package pl.mqb.dao;

import pl.mqb.model.Account;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class AccountRepository {

    private static final AccountRepository instance = new AccountRepository();
    private final ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();

    private AccountRepository() {}

    public static AccountRepository getInstance() {
        return instance;
    }

    public void removeAll() {
        //added only for test purposes
        accounts.clear();
    }

    public Account addAccount(Account account) {
        return accounts.putIfAbsent(account.getId(), account);
    }

    public Account getById(String id) {
        return accounts.get(id);
    }

    public Collection<Account> getAll() {
        return accounts.values();
    }
}
