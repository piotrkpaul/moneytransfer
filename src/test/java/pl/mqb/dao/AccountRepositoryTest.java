package pl.mqb.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.mqb.error.DuplicateAccountException;
import pl.mqb.model.Account;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountRepositoryTest {

    private AccountRepository repository = AccountRepository.getInstance();

    @BeforeEach
    void cleanUp() {
        repository.removeAll();
    }

    @Test
    @DisplayName("It's possible to add new account")
    void addingAccountShouldSucceed() {
        Account anyAccount = new Account();

        repository.addAccount(anyAccount);

        assertEquals(1, repository.getAll().size());
        assertEquals(anyAccount, repository.getById(anyAccount.getId()));
    }

    @Test
    @DisplayName("Adding duplicate account should throw DuplicateAccountException")
    void addingDuplicateShouldReturnAlreadyPersistedAccount() {
        String accountId = "1337";
        Account testAccount = new Account(accountId, "100000");

        repository.addAccount(testAccount);

        Account accountWithDuplicatedId = new Account(accountId, "0");

        assertThrows(DuplicateAccountException.class, () -> repository.addAccount(accountWithDuplicatedId));
        assertEquals(1, repository.getAll().size());
    }

    @Test
    @DisplayName("It's possible to remove all accounts")
    void shouldBePossibleToRemoveAllAccounts() {
        int numberOfAccounts = 5;

        insertAccountsIntoRepository(numberOfAccounts);

        assertEquals(numberOfAccounts, repository.getAll().size());

        repository.removeAll();

        assertEquals(0, repository.getAll().size());
    }

    @Test
    @DisplayName("It's possible to retrieve account from database")
    void shouldReturnExistingAccount() {
        final String existingId = "1";
        Account account = new Account(existingId, "10");

        repository.addAccount(account);

        Account retrievedAccount = repository.getById(existingId);

        assertEquals(account, retrievedAccount);
    }

    private void insertAccountsIntoRepository(int numberOfAccounts) {
        IntStream.range(0, numberOfAccounts).forEach(a -> repository.addAccount(new Account()));
    }

}