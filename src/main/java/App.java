import config.ApplicationServer;
import dao.AccountRepository;
import model.Account;

public class App {

    public static void main(java.lang.String[] args) {
        ApplicationServer.startServer();
    }


    private static void initializeTestData() {

        AccountRepository repository = AccountRepository.getInstance();
        repository.addAccount(new Account("1", "100.10"));
        repository.addAccount(new Account("2", "90.22"));
        repository.addAccount(new Account("3", "20.22"));
    }
}
