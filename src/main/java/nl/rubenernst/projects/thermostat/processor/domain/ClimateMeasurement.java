package nl.rubenernst.projects.thermostat.processor.domain;

import lombok.Data;

@Data
public class ClimateMeasurement {
    private final String room;
    private final ClimateType type;
    private final float value;
}
