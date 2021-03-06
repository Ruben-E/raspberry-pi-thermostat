package nl.rubenernst.projects.thermostat.processor.domain;

import lombok.Data;

@Data
public class ManualPreferredTemperature {
    private final String room;
    private final int temperature;
}
