package nl.rubenernst.projects.thermostat.processor.repository;

import nl.rubenernst.projects.thermostat.processor.domain.RadiatorStatus;
import nl.rubenernst.projects.thermostat.processor.exceptions.RepositoryException;
import org.springframework.stereotype.Repository;

@Repository
public class RadiatorStatusRepository {
    private RadiatorStatus currentRadiatorStatus = RadiatorStatus.OFF;

    public RadiatorStatus findLatest() throws RepositoryException {
        return currentRadiatorStatus;
    }

    public void save(RadiatorStatus currentRadiatorStatus) throws RepositoryException {
        this.currentRadiatorStatus = currentRadiatorStatus;
    }
}
