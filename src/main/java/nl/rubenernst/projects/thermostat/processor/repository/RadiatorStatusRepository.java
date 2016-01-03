package nl.rubenernst.projects.thermostat.processor.repository;

import nl.rubenernst.projects.thermostat.processor.domain.RadiatorStatus;
import nl.rubenernst.projects.thermostat.processor.exceptions.RepositoryException;
import org.springframework.stereotype.Repository;

@Repository
public class RadiatorStatusRepository {
//    public static final String TABLE_NAME = "overwritten_preferred_temperatures";
//    public static final String ID_COLUMN = "id";
//    public static final String OVERWRITTEN_REFERRED_TEMPERATURE_COLUMN = "overwritten_preferred_temperature";
//    public static final String START_COLUMN = "start";
//    public static final String END_COLUMN = "end";
//    TODO: Save in database

//    @Autowired
//    private RethinkDbConnectionFactory rethinkDbConnectionFactory;

    private RadiatorStatus currentRadiatorStatus = RadiatorStatus.OFF;

    public RadiatorStatus findLatest() throws RepositoryException {
        return currentRadiatorStatus;
    }

    public void save(RadiatorStatus currentRadiatorStatus) throws RepositoryException {
        this.currentRadiatorStatus = currentRadiatorStatus;
    }

    //    public OverwrittenPreferredTemperature getCurrentOverwrittenPreferredTemperature() throws RepositoryException {
//        try {
//            Connection connection = rethinkDbConnectionFactory.getConnection();
//
//            Cursor<HashMap> cursor = RethinkDB.r.table(TABLE_NAME).filter(row -> {
//                return row.g(END_COLUMN).eq(null);
//            }).run(connection);
//
//            HashMap next = cursor.next();
//            OverwrittenPreferredTemperature overwrittenPreferredTemperature = new OverwrittenPreferredTemperature((Float) next.get(OVERWRITTEN_REFERRED_TEMPERATURE_COLUMN));
//
//            connection.close();
//
//            return overwrittenPreferredTemperature;
//        } catch (Exception e) {
//            throw new RepositoryException(e);
//        }
//    }
}
