package pl.mqb.config;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.mqb.dao.AccountRepository;
import pl.mqb.model.Account;


/**
 * Class responsible for initialization and start of Jetty with Jersey ServletContainer
 * <p>
 * default port 8080
 * default URL: http://localhost:8080
 */
public class ApplicationServer {

    private static final Logger log = LoggerFactory.getLogger(ApplicationServer.class);
    private static final int SERVER_PORT = 8080;
    private static final String CONTEXT_PATH = "/*";
    private ApplicationServer() {
    }

    private static Server getServer() {
        ApplicationConfig config = new ApplicationConfig();
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));
        Server server = new Server(SERVER_PORT);
        ServletContextHandler context = new ServletContextHandler(server, "/*");
        context.addServlet(servlet, CONTEXT_PATH);
        return server;
    }

    public static void startServer() {
        Server server = getServer();

        initTestData();

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            log.error("Server exception: " + e.getClass() + " " + e.getMessage());
            System.exit(1);
        } finally {
            server.destroy();
        }
    }

    private static void initTestData() {

        AccountRepository repository = AccountRepository.getInstance();
        repository.addAccount(new Account("1", "100.10"));
        repository.addAccount(new Account("2", "90.22"));
        repository.addAccount(new Account("3", "20.22"));
        log.info("System has been initialized with test data.");
    }
}
