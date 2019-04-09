package config;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

class ApplicationConfig extends ResourceConfig {

    ApplicationConfig() {
        registerJacksonJsonMapper();
        packages("rest");
    }

    private void registerJacksonJsonMapper() {
        register(JacksonFeature.class);
    }

}
