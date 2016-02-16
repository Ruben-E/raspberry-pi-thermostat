package nl.rubenernst.projects.thermostat.processor.repository;

import nl.rubenernst.projects.thermostat.processor.domain.ClimateMeasurement;
import nl.rubenernst.projects.thermostat.processor.domain.ClimateType;
import nl.rubenernst.projects.thermostat.processor.exceptions.RepositoryException;
import org.springframework.stereotype.Repository;

@Repository
public class MeasurementRepository {
    private ClimateMeasurement latestTemperature;
    private ClimateMeasurement latestHumidity;

    public void save(ClimateMeasurement climateMeasurement) throws RepositoryException {
        if (climateMeasurement.getType().equals(ClimateType.TEMPERATURE)) {
            latestTemperature = climateMeasurement;
        }

        if (climateMeasurement.getType().equals(ClimateType.HUMIDITY)) {
            latestHumidity = climateMeasurement;
        }
    }

    public ClimateMeasurement findLatestByType(ClimateType climateType) {
        if (climateType.equals(ClimateType.TEMPERATURE)) {
            return latestTemperature;
        }

        if (climateType.equals(ClimateType.HUMIDITY)) {
            return latestHumidity;
        }

        return null;
    }
}
