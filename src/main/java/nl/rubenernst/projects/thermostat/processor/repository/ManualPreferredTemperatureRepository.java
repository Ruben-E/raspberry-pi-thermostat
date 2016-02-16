package nl.rubenernst.projects.thermostat.processor.repository;

import nl.rubenernst.projects.thermostat.processor.domain.ManualPreferredTemperature;
import nl.rubenernst.projects.thermostat.processor.exceptions.RepositoryException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class ManualPreferredTemperatureRepository {
    @Value("${preferred.temperature.default}")
    private int defaultPreferredTemperature;

//    public static final String TABLE_NAME = "manual_preferred_temperatures";
//    public static final String END_COLUMN = "end";
//
//    @Autowired
//    private RethinkDbConnectionFactory rethinkDbConnectionFactory;
//
//    public void findLatest() throws RepositoryException {
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

    private ManualPreferredTemperature currentManualPreferredTemperature = new ManualPreferredTemperature("living", defaultPreferredTemperature);

    public ManualPreferredTemperature findLatest() throws RepositoryException {
        return currentManualPreferredTemperature;
    }

    public void save(ManualPreferredTemperature manualPreferredTemperature) throws RepositoryException {
        currentManualPreferredTemperature = manualPreferredTemperature;
    }
}
