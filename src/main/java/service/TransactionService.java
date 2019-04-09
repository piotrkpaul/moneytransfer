package service;

import dao.AccountRepository;
import error.InsufficientBalanceException;
import model.Account;
import model.MoneyTransfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public enum TransactionService {

    INSTANCE;

     //tieLock is used for deadlock prevention (in a rare case when both accounts are locked by different threads).
    private static final Object tieLock = new Object();
    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
    private final AccountRepository repository = AccountRepository.getInstance();

    public void transfer(final MoneyTransfer trx) {
        Account source = repository.getById(trx.getSource());
        Account target = repository.getById(trx.getTarget());

        transferMoney(source, target, trx.getAmount());
    }

    private void transferMoney(final Account sourceAccount,
                               final Account targetAccount,
                               final BigDecimal amount) {
        class TransferExecutor {
            private void execute() {
                if (sourceAccount.getBalance().compareTo(amount) < 0) {
                    throw new InsufficientBalanceException("Money Transfer can't be performed due to lack of funds on account.");
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
