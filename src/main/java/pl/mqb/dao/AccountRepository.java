package pl.mqb.dao;

import pl.mqb.error.DuplicateAccountException;
import pl.mqb.model.Account;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccountRepository {

    private static final AccountRepository INSTANCE = new AccountRepository(new ConcurrentHashMap<>());
    private final Map<String, Account> accounts;

    private AccountRepository(Map<String, Account> accounts) {
        this.accounts = accounts;
    }

    public static AccountRepository getInstance() {
        return INSTANCE;
    }

    public Account getById(String id) {
        return accounts.get(id);
    }

    public Collection<Account> getAll() {
        return accounts.values();
    }

    public Account addAccount(Account account) {
        Account accountExists = accounts.putIfAbsent(account.getId(), account);
        if (accountExists != null) {
            throw new DuplicateAccountException(accountExists.getId());
        }

        return getById(account.getId());
    }

    public void removeAll() {
        synchronized (accounts) {
            accounts.clear();
        }
    }
}
