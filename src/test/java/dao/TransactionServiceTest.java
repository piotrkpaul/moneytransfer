package dao;

import error.InsufficientBalanceException;
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

class TransactionServiceTest {

    private final static Logger log = LoggerFactory.getLogger(TransactionServiceTest.class);
    private static final String ACCOUNT_ID_2 = "2";
    private static final String ACCOUNT_ID_1 = "1";

    private Account accountA;
    private Account accountB;

    private AccountRepository repository = AccountRepository.getInstance();
    private TransactionService transactionService = TransactionService.INSTANCE;

    @BeforeEach
    void setUp() {
        repository.removeAll();

        accountA = new Account(ACCOUNT_ID_1, "100.12");
        accountB = new Account(ACCOUNT_ID_2, "99.23");

        repository.addAccount(accountA);
        repository.addAccount(accountB);
    }

    @Test
    void successfulTransaction() {
        log.info("Before trx: " + repository.getAll());

        MoneyTransfer trx = new MoneyTransfer(ACCOUNT_ID_1, ACCOUNT_ID_2, "10.00");
        log.info("Trx details: " + trx);

        transactionService.transfer(trx);

        log.info("After trx: " + repository.getAll());

        assertEquals(new BigDecimal("90.12"), repository.getById(accountA.getId()).getBalance());
        assertEquals(new BigDecimal("109.23"), repository.getById(accountB.getId()).getBalance());
    }

    @Test
    void insufficientBalanceTest() {
        final String lowBalanceAccountId = "3";
        Account lowBalanceAccount = new Account(lowBalanceAccountId, "0.12");
        repository.addAccount(lowBalanceAccount);

        log.info("Before trx: " + repository.getAll());
        MoneyTransfer trx = new MoneyTransfer(lowBalanceAccountId, ACCOUNT_ID_2, "10.00");
        log.info("Trx: " + trx);

        assertThrows(InsufficientBalanceException.class, () -> transactionService.transfer(trx));

        log.info("After trx: " + repository.getAll());

        assertEquals(new BigDecimal("0.12"), repository.getById(lowBalanceAccount.getId()).getBalance());
        assertEquals(new BigDecimal("99.23"), repository.getById(ACCOUNT_ID_2).getBalance());
    }

}