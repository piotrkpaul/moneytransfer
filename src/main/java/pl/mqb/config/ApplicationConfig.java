package pl.mqb.config;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;


/**
 * Jersey resource configuration class.
 */
class ApplicationConfig extends ResourceConfig {

    ApplicationConfig() {
        registerJacksonJsonMapper();
        packages("pl/mqb/rest");
    }

    private void registerJacksonJsonMapper() {
        register(JacksonFeature.class);
    }

}
