package nl.rubenernst.projects.thermostat.processor.configuration.rethinkdb;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeoutException;


@Component
@Slf4j
public class RethinkDbConnectionFactory {
    @Value("${rethinkdb.host}")
    private String rethinkDbHost;

    @Value("${rethinkdb.port}")
    private int rethinkDbPort;

    public Connection getConnection() {
        try {
            return RethinkDB.r.connection().hostname(rethinkDbHost).port(rethinkDbPort).connect();
        } catch (TimeoutException e) {
            log.error("Could not connect to RethinkDB ({}:{})", rethinkDbHost, rethinkDbPort, e);
            return null;
        }
    }
}
