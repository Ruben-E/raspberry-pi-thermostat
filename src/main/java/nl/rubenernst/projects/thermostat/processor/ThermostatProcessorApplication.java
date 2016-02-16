package nl.rubenernst.projects.thermostat.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@IntegrationComponentScan
public class ThermostatProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThermostatProcessorApplication.class, args);
	}
}
