package pl.mqb.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mqb.model.Account;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AccountRepositoryTest {

    private AccountRepository repository = AccountRepository.getInstance();

    @BeforeEach
    void cleanUp() {
        repository.removeAll();
    }

    @Test
    void addingAccountShouldSucceed() {
        assertEquals(0, repository.getAll().size());

        Account anyAccount = new Account();

        repository.addAccount(anyAccount);

        assertEquals(1, repository.getAll().size());
        assertEquals(anyAccount, repository.getById(anyAccount.getId()));
    }

    @Test
    void addingDuplicateShouldReturnAlreadyPersistedAccount() {
        assertEquals(0, repository.getAll().size());

        String accountId = "1337";
        Account testAccount = new Account(accountId, "100000");

        Account response1 = repository.addAccount(testAccount);

        Account accountWithDuplicatedId = new Account(accountId, "0");
        Account responseDuplicate = repository.addAccount(accountWithDuplicatedId);

        assertNull(response1);
        assertEquals(testAccount, responseDuplicate);
        assertEquals(1, repository.getAll().size());
    }

    @Test
    void shouldBePossibleToRemoveAllAccounts() {
        assertEquals(0, repository.getAll().size());

        int numberOfAccounts = 5;
        insertAccountsIntoRepository(numberOfAccounts);

        assertEquals(numberOfAccounts, repository.getAll().size());

        repository.removeAll();

        assertEquals(0, repository.getAll().size());
    }

    private void insertAccountsIntoRepository(int numberOfAccounts) {
        IntStream.range(0, numberOfAccounts).forEach(a -> repository.addAccount(new Account()));
    }

}