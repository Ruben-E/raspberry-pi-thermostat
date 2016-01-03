package nl.rubenernst.projects.thermostat.processor.service;

import lombok.extern.slf4j.Slf4j;
import nl.rubenernst.projects.thermostat.processor.domain.*;
import nl.rubenernst.projects.thermostat.processor.exceptions.RepositoryException;
import nl.rubenernst.projects.thermostat.processor.repository.MeasurementRepository;
import nl.rubenernst.projects.thermostat.processor.repository.ManualPreferredTemperatureRepository;
import nl.rubenernst.projects.thermostat.processor.repository.RadiatorStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ThermostatService {
    @Autowired
    private RadiatorStatusRepository radiatorStatusRepository;

    @Autowired
    private ManualPreferredTemperatureRepository manualPreferredTemperatureRepository;

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private RadiatorStatus defaultRadiatorStatus;

    @Value("${preferred.temperature.default}")
    private int defaultPreferredTemperature;

    @Value("${preferred.temperature.offset.lower}")
    private int lowerOffset;

    @Value("${preferred.temperature.offset.upper}")
    private int upperOffset;

    private RadiatorStatus getRadiatorStatus() {
        try {
            return radiatorStatusRepository.findLatest();
        } catch (RepositoryException e) {
            log.warn("Could not get the current radiator status. Returning the default radior status: " + defaultRadiatorStatus);

            return defaultRadiatorStatus;
        }
    }

    private void turnOnRadiator() {
        try {
            //TODO: Send event
            radiatorStatusRepository.save(RadiatorStatus.ON);
        } catch (RepositoryException e) {
            log.warn("Could not turn on the radiator", e);
        }
    }

    private void turnOffRadiator() {
        try {
            //TODO: Send event
            radiatorStatusRepository.save(RadiatorStatus.OFF);
        } catch (RepositoryException e) {
            log.warn("Could not turn on the radiator", e);
        }
    }

    private PreferredTemperature getCurrentPreferredTemperature() {
        // TODO: It only supports the latest preferred temperature
        // TODO: Also support a schedule

        try {
            ManualPreferredTemperature currentManualPreferredTemperature = manualPreferredTemperatureRepository.findLatest();
            return new PreferredTemperature(currentManualPreferredTemperature.getTemperature());
        } catch (RepositoryException e) {
            log.warn("Could not get the manual preferred temperature. Returning the default preferred temperature: " + defaultPreferredTemperature);

            return new PreferredTemperature(defaultPreferredTemperature);
        }
    }

    private void toggleRadiatorIfNeeded() {
        try {
            RadiatorStatus radiatorStatus = getRadiatorStatus();
            PreferredTemperature currentPreferredTemperature = getCurrentPreferredTemperature();
            ClimateMeasurement currentTemperature = measurementRepository.findLatestByType(ClimateType.TEMPERATURE);
            int lowerThreshold = currentPreferredTemperature.getTemperature() - lowerOffset;
            int upperThreshold = currentPreferredTemperature.getTemperature() - upperOffset;

            if (currentTemperature == null) {
                log.warn("Current temperature is unknown...");
                // Do nothing because the current temperature is unknown
                return;
            }

            if (radiatorStatus.equals(RadiatorStatus.OFF)) {
                if (currentTemperature.getValue() < lowerThreshold) {
                    turnOnRadiator();
                }
            } else {
                if (currentTemperature.getValue() > upperThreshold) {
                    turnOffRadiator();
                }
            }
        } catch (Exception e) {
            log.warn("Got exception while toggling the radiator if needed", e);
        }
    }

    public void handleMeasurement(ClimateMeasurement climateMeasurement) {
        try {
            measurementRepository.save(climateMeasurement);

            toggleRadiatorIfNeeded();
        } catch (RepositoryException e) {
            log.warn("Could not save the measurement to the database: " + climateMeasurement + ". Ignoring to be resilient.", e);
        }
    }
}
