package pl.mqb.service;

import pl.mqb.dao.AccountRepository;
import pl.mqb.dto.AccountDTO;
import pl.mqb.error.InsufficientBalanceException;
import pl.mqb.model.Account;
import pl.mqb.model.MoneyTransfer;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TransactionService {

    //tieLock used to prevent deadlock (in a rare case when both accounts has the same hashcode).
    private static final Object tieLock = new Object();
    private static final TransactionService INSTANCE = new TransactionService(AccountRepository.getInstance());

    private final AccountRepository repository;

    private TransactionService(AccountRepository repository) {
        this.repository = repository;
    }

    public static TransactionService getInstance() {
        return INSTANCE;
    }

    public List<AccountDTO> transfer(final MoneyTransfer trx) {
        Account source = repository.getById(trx.getSource());
        Account target = repository.getById(trx.getTarget());

        return transferMoney(source, target, trx.getAmount());
    }

    private List<AccountDTO> transferMoney(final Account sourceAccount,
                                           final Account targetAccount,
                                           final BigDecimal amount) {
        class TransferExecutor {
            private List<AccountDTO> execute() {
                if (sourceAccount.getBalance().compareTo(amount) < 0)
                    throw new InsufficientBalanceException("Money Transfer can't be performed due to lack of funds on the account.");

                sourceAccount.debit(amount);
                targetAccount.credit(amount);

                return Collections.unmodifiableList(
                        Arrays.asList(AccountDTO.from(sourceAccount), AccountDTO.from(targetAccount)));
            }
        }

        int sourceHash = System.identityHashCode(sourceAccount);
        int targetHash = System.identityHashCode(targetAccount);

        if (sourceHash < targetHash) {
            synchronized (sourceAccount) {
                synchronized (targetAccount) {
                    return new TransferExecutor().execute();
                }
            }
        } else if (sourceHash > targetHash) {
            synchronized (sourceAccount) {
                synchronized (targetAccount) {
                    return new TransferExecutor().execute();
                }
            }
        } else {
            synchronized (tieLock) {
                synchronized (sourceAccount) {
                    synchronized (targetAccount) {
                        return new TransferExecutor().execute();
                    }
                }
            }
        }
    }
}
