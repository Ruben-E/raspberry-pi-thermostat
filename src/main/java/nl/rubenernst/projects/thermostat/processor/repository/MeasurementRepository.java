package nl.rubenernst.projects.thermostat.processor.repository;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import nl.rubenernst.projects.thermostat.processor.domain.ClimateMeasurement;
import nl.rubenernst.projects.thermostat.processor.exceptions.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MeasurementRepository {
    @Autowired
    private String rethinkDbHost;

    @Autowired
    private int rethinkDbPort;

    public void save(ClimateMeasurement climateMeasurement) throws RepositoryException {
        try {
            Connection connection = RethinkDB.r.connection().hostname(rethinkDbHost).port(rethinkDbPort).connect();

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
}
