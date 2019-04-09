package pl.mqb.model;

import org.junit.jupiter.api.Test;
import pl.mqb.error.InsufficientBalanceException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    @Test
    void successfulAccountDebit() {
        Account testAccount = new Account("100.10");

        BigDecimal balanceAfterDebit = testAccount.debit(new BigDecimal("20.20"));

        assertEquals(new BigDecimal("79.90"), balanceAfterDebit);
        assertEquals(new BigDecimal("79.90"), testAccount.getBalance());
    }

    @Test
    void successfulDebitToZero() {
        Account testAccount = new Account("100");

        BigDecimal balanceAfterDebit = testAccount.debit(new BigDecimal("100"));

        assertEquals(BigDecimal.ZERO, balanceAfterDebit);
        assertEquals(BigDecimal.ZERO, testAccount.getBalance());
    }


    @Test
    void accountDebitBelowLimitShouldThrowException() {
        Account testAccount = new Account("100");

        assertThrows(InsufficientBalanceException.class, () -> testAccount.debit(new BigDecimal("101")));
    }

    @Test
    void negativeDebitShouldThrowException() {
        Account testAccount = new Account();

        assertThrows(IllegalArgumentException.class, () -> testAccount.debit(new BigDecimal("-30.28")));
    }

    @Test
    void nullDebitShouldThrowException() {
        Account testAccount = new Account();

        assertThrows(IllegalArgumentException.class, () -> testAccount.debit(null));
    }

    @Test
    void successfulAccountCredit() {
        Account testAccount = new Account("50.12");

        BigDecimal balanceAfterCredit = testAccount.credit(new BigDecimal("30.28"));

        assertEquals(new BigDecimal("80.40"), balanceAfterCredit);
        assertEquals(new BigDecimal("80.40"), testAccount.getBalance());
    }

    @Test
    void negativeCreditShouldThrowException() {
        Account testAccount = new Account();

        assertThrows(IllegalArgumentException.class, () -> testAccount.credit(new BigDecimal("-30.28")));
    }

    @Test
    void nullCreditShouldThrowException() {
        Account testAccount = new Account();

        assertThrows(IllegalArgumentException.class, () -> testAccount.credit(null));
    }


}