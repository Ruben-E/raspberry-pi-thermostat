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

    @Bean
    public int defaultPreferredTemperature() {
        return 1950;
    }

    @Bean
    public int lowerThreshold() {
        return 30;
    }

    @Bean
    public int upperThreshold() {
        return 30;
    }
}
