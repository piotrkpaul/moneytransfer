package dao;

import model.Account;
import model.MoneyTransfer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.TransactionService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountRepositoryTest {

    private final static Logger log = LoggerFactory.getLogger(AccountRepositoryTest.class);

    private AccountRepository repository = AccountRepository.getInstance();
    private TransactionService transactionService = TransactionService.INSTANCE;

    @BeforeEach
    void setUp() {
        repository.removeAll();
    }

    @Test
    void successfulTransaction() {
        Account a1 = new Account("1", "100.12");
        Account a2 = new Account("2", "99.23");

        repository.create(a1);
        repository.create(a2);

        log.info("Before trx: " + repository.getAll());

        MoneyTransfer trx = new MoneyTransfer("1", "2", "10.00");
        log.info("Trx details: " + trx);

        transactionService.transfer(trx);

        log.info("After trx: " + repository.getAll());

        assertEquals(new BigDecimal("90.12"), repository.getById(a1.getId()).getBalance());
        assertEquals(new BigDecimal("109.23"), repository.getById(a2.getId()).getBalance());
    }

    @Test
    void unsatisfiedBalanceError() {
        Account a1 = new Account("1", "0.12");
        Account a2 = new Account("2", "99.23");

        repository.create(a1);
        repository.create(a2);

        log.info("Before trx: " + repository.getAll());

        MoneyTransfer trx = new MoneyTransfer("1", "2", "10.00");
        log.info("Trx details: " + trx);

        assertThrows(IllegalArgumentException.class, () -> transactionService.transfer(trx));

        log.info("After trx: " + repository.getAll());

        assertEquals(new BigDecimal("0.12"), repository.getById(a1.getId()).getBalance());
        assertEquals(new BigDecimal("99.23"), repository.getById(a2.getId()).getBalance());
    }

}