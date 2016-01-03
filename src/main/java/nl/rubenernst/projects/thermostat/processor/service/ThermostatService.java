package nl.rubenernst.projects.thermostat.processor.service;

import lombok.extern.slf4j.Slf4j;
import nl.rubenernst.projects.thermostat.processor.domain.OverwrittenPreferredTemperature;
import nl.rubenernst.projects.thermostat.processor.domain.PreferredTemperature;
import nl.rubenernst.projects.thermostat.processor.domain.RadiatorStatus;
import nl.rubenernst.projects.thermostat.processor.exceptions.RepositoryException;
import nl.rubenernst.projects.thermostat.processor.repository.OverwrittenPreferredTemperatureRepository;
import nl.rubenernst.projects.thermostat.processor.repository.RadiatorStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ThermostatService {
    @Autowired
    private RadiatorStatusRepository radiatorStatusRepository;

    @Autowired
    private RadiatorStatus defaultRadiatorStatus;

    @Autowired
    private OverwrittenPreferredTemperatureRepository overwrittenPreferredTemperatureRepository;

    @Autowired
    private int defaultPreferredTemperature;

    @Autowired
    private int lowerThreshold;

    @Autowired
    private int upperThreshold;

    public RadiatorStatus getRadiatorStatus() {
        try {
            return radiatorStatusRepository.getCurrentRadiatorStatus();
        } catch (RepositoryException e) {
            log.warn("Could not get the current radiator status. Returning the default radior status: " + defaultRadiatorStatus);

            return defaultRadiatorStatus;
        }
    }

    public PreferredTemperature getCurrentPreferredTemperature() {
        // TODO: It only supports the latest preferred temperature
        // TODO: Also support a schedule

        try {
            OverwrittenPreferredTemperature currentOverwrittenPreferredTemperature = overwrittenPreferredTemperatureRepository.getCurrentOverwrittenPreferredTemperature();
            return new PreferredTemperature(currentOverwrittenPreferredTemperature.getTemperature());
        } catch (RepositoryException e) {
            log.warn("Could not get the overwritten preferred temperature. Returning the default preferred temperature: " + defaultPreferredTemperature);

            return new PreferredTemperature(defaultPreferredTemperature);
        }
    }

    public void toggleRadiatorIfNeeded() {
        RadiatorStatus radiatorStatus = getRadiatorStatus();
        PreferredTemperature currentPreferredTemperature = getCurrentPreferredTemperature();

        if (radiatorStatus.equals(RadiatorStatus.OFF)) {
            if ()
        }
    }
}
