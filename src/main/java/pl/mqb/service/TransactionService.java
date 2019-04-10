package pl.mqb.service;

import pl.mqb.dao.AccountRepository;
import pl.mqb.error.InsufficientBalanceException;
import pl.mqb.model.Account;
import pl.mqb.model.MoneyTransfer;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum TransactionService {

    INSTANCE;

     //tieLock used to prevent deadlock (in a rare case when both accounts are locked by different threads).
    private static final Object tieLock = new Object();
    private final AccountRepository repository = AccountRepository.getInstance();

    public List transfer(final MoneyTransfer trx) {
        Account source = repository.getById(trx.getSource());
        Account target = repository.getById(trx.getTarget());

        transferMoney(source, target, trx.getAmount());

        return Collections.unmodifiableList(Arrays.asList(source, target));
    }

    private void transferMoney(final Account sourceAccount,
                               final Account targetAccount,
                               final BigDecimal amount) {
        class TransferExecutor {
            private void execute() {
                if (sourceAccount.getBalance().compareTo(amount) < 0) {
                    throw new InsufficientBalanceException("Money Transfer can't be performed due to lack of funds on the account.");
                }
                sourceAccount.debit(amount);
                targetAccount.credit(amount);
            }
        }

        int sourceHash = System.identityHashCode(sourceAccount);
        int targetHash = System.identityHashCode(targetAccount);

        if (sourceHash < targetHash) {
            synchronized (sourceAccount) {
                synchronized (targetAccount) {
                    new TransferExecutor().execute();
                }
            }
        } else if (sourceHash > targetHash) {
            synchronized (sourceAccount) {
                synchronized (targetAccount) {
                    new TransferExecutor().execute();
                }
            }
        } else {
            synchronized (tieLock) {
                synchronized (sourceAccount) {
                    synchronized (targetAccount) {
                        new TransferExecutor().execute();
                    }
                }
            }
        }
    }
}
