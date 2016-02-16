package nl.rubenernst.projects.thermostat.processor.repository;

import nl.rubenernst.projects.thermostat.processor.domain.ManualPreferredTemperature;
import nl.rubenernst.projects.thermostat.processor.exceptions.RepositoryException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class ManualPreferredTemperatureRepository {
    @Value("${preferred.temperature.default}")
    private int defaultPreferredTemperature;

    private ManualPreferredTemperature currentManualPreferredTemperature = new ManualPreferredTemperature("living", defaultPreferredTemperature);

    public ManualPreferredTemperature findLatest() throws RepositoryException {
        return currentManualPreferredTemperature;
    }

    public void save(ManualPreferredTemperature manualPreferredTemperature) throws RepositoryException {
        currentManualPreferredTemperature = manualPreferredTemperature;
    }
}
