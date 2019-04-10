package pl.mqb.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.mqb.error.IllegalOperationException;
import pl.mqb.error.InsufficientBalanceException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    @Test
    @DisplayName("It's possible to debit an account")
    void successfulAccountDebit() {
        Account testAccount = new Account("100.10");

        BigDecimal balanceAfterDebit = testAccount.debit(new BigDecimal("20.20"));

        assertEquals(new BigDecimal("79.90"), balanceAfterDebit);
        assertEquals(new BigDecimal("79.90"), testAccount.getBalance());
    }

    @Test
    @DisplayName("It's possible to debit to zero")
    void successfulDebitToZero() {
        Account testAccount = new Account("100");

        BigDecimal balanceAfterDebit = testAccount.debit(new BigDecimal("100"));

        assertEquals(BigDecimal.ZERO, balanceAfterDebit);
        assertEquals(BigDecimal.ZERO, testAccount.getBalance());
    }


    @Test
    @DisplayName("Debit below zero throws InsufficientBalanceException")
    void accountDebitBelowLimitShouldThrowException() {
        Account testAccount = new Account("100");

        assertThrows(InsufficientBalanceException.class, () -> testAccount.debit(new BigDecimal("101")));
    }

    @Test
    @DisplayName("It's not possible to pass negative debit value - throws IllegalOperationException")
    void negativeDebitShouldThrowException() {
        Account testAccount = new Account();

        assertThrows(IllegalOperationException.class, () -> testAccount.debit(new BigDecimal("-30.28")));
    }

    @Test
    @DisplayName("It's not possible to pass null as debit value - throws IllegalOperationException")
    void nullDebitShouldThrowException() {
        Account testAccount = new Account();

        assertThrows(IllegalOperationException.class, () -> testAccount.debit(null));
    }

    @Test
    @DisplayName("It's possible to credit an account")
    void successfulAccountCredit() {
        Account testAccount = new Account("50.12");

        BigDecimal balanceAfterCredit = testAccount.credit(new BigDecimal("30.28"));

        assertEquals(new BigDecimal("80.40"), balanceAfterCredit);
        assertEquals(new BigDecimal("80.40"), testAccount.getBalance());
    }

    @Test
    @DisplayName("It's not possible to pass negative credit value - throws IllegalOperationException")
    void negativeCreditShouldThrowException() {
        Account testAccount = new Account();

        assertThrows(IllegalOperationException.class, () -> testAccount.credit(new BigDecimal("-30.28")));
    }

    @Test
    @DisplayName("It's not possible to pass null credit value - throws IllegalOperationException")
    void nullCreditShouldThrowException() {
        Account testAccount = new Account();

        assertThrows(IllegalOperationException.class, () -> testAccount.credit(null));
    }
}