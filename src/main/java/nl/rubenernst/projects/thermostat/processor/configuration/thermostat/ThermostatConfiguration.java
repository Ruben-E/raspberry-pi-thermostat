package nl.rubenernst.projects.thermostat.processor.configuration.thermostat;

import nl.rubenernst.projects.thermostat.processor.domain.RadiatorStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThermostatConfiguration {
    @Bean
    public RadiatorStatus defaultRadiatorStatus() {
        return RadiatorStatus.OFF;
    }
}
