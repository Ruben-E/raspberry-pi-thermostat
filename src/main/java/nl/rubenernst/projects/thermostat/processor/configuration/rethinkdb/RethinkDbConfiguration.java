package nl.rubenernst.projects.thermostat.processor.configuration.rethinkdb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RethinkDbConfiguration {

    @Bean
    public String rethinkDbHost() {
        return "localhost";
    }

    @Bean
    public int rethinkDbPort() {
        return 28015;
    }
}
