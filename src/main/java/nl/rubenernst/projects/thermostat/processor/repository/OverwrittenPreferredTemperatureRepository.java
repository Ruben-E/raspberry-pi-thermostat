package nl.rubenernst.projects.thermostat.processor.repository;

import nl.rubenernst.projects.thermostat.processor.domain.OverwrittenPreferredTemperature;
import nl.rubenernst.projects.thermostat.processor.exceptions.RepositoryException;
import org.springframework.stereotype.Repository;

@Repository
public class OverwrittenPreferredTemperatureRepository {
//    public static final String TABLE_NAME = "overwritten_preferred_temperatures";
//    public static final String END_COLUMN = "end";
//
//    @Autowired
//    private RethinkDbConnectionFactory rethinkDbConnectionFactory;
//
//    public void getCurrentOverwrittenPreferredTemperature() throws RepositoryException {
//        try {
//            Connection connection = rethinkDbConnectionFactory.getConnection();
//
//            RethinkDB.r.table(TABLE_NAME).filter(row -> {
//                return row.g(END_COLUMN).eq(null);
//            }).run(connection);
//
//            connection.close();
//        } catch (Exception e) {
//            throw new RepositoryException(e);
//        }
//    }
//    TODO: Save in database

    private OverwrittenPreferredTemperature currentOverwrittenPreferredTemperature = new OverwrittenPreferredTemperature(1950);

    public OverwrittenPreferredTemperature getCurrentOverwrittenPreferredTemperature() throws RepositoryException{
        return currentOverwrittenPreferredTemperature;
    }
}
