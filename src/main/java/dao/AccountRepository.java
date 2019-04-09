package dao;

import model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class AccountRepository {

    private static final AccountRepository instance = new AccountRepository();
    private final ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();

    private AccountRepository() {}

    public static AccountRepository getInstance() {
        return instance;
    }

    void removeAll() {
        accounts.clear();
    }

    public Account create(Account account) {
        return accounts.putIfAbsent(account.getId(), account);
    }

    public Account getById(String id) {
        return accounts.get(id);
    }

    public List<Account> getAll() {
        return new ArrayList<>(accounts.values());
    }
}
