package nl.rubenernst.projects.thermostat.processor.repository;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import nl.rubenernst.projects.thermostat.processor.configuration.rethinkdb.RethinkDbConnectionFactory;
import nl.rubenernst.projects.thermostat.processor.domain.ClimateMeasurement;
import nl.rubenernst.projects.thermostat.processor.domain.ClimateType;
import nl.rubenernst.projects.thermostat.processor.exceptions.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MeasurementRepository {
    @Autowired
    private RethinkDbConnectionFactory rethinkDbConnectionFactory;

    private ClimateMeasurement latestTemperature;

    private ClimateMeasurement latestHumidity;

    public void save(ClimateMeasurement climateMeasurement) throws RepositoryException {
        if (climateMeasurement.getType().equals(ClimateType.TEMPERATURE)) {
            latestTemperature = climateMeasurement;
        }

        if (climateMeasurement.getType().equals(ClimateType.HUMIDITY)) {
            latestHumidity = climateMeasurement;
        }

        try {
            Connection connection = rethinkDbConnectionFactory.getConnection();

            RethinkDB.r.table("measurements").insert(
                    RethinkDB.r.hashMap("room", climateMeasurement.getRoom())
                            .with("type", climateMeasurement.getType().toString())
                            .with("value", climateMeasurement.getValue())
            ).run(connection);

            connection.close();
        } catch (Exception e) {
            throw new RepositoryException(e);
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
