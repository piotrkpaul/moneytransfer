package pl.mqb;

import pl.mqb.config.ApplicationServer;

public class App {

    public static void main(java.lang.String[] args) {
        String withTestData = args[0];
        ApplicationServer.startServer(withTestData);
    }
}
