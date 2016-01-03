package nl.rubenernst.projects.thermostat.processor.handler;

import nl.rubenernst.projects.thermostat.processor.domain.ClimateMeasurement;
import nl.rubenernst.projects.thermostat.processor.domain.ClimateType;
import nl.rubenernst.projects.thermostat.processor.exceptions.InvalidMeasurement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ClimateMeasurementMapper {
    private static final String VALID_MEASUREMENT = "^\\|(.+)\\|(.+)\\|(\\d{4})\\|$";

    public static ClimateMeasurement convertToClimateMeasurement(String measurement) throws InvalidMeasurement {
        Pattern pattern = Pattern.compile(VALID_MEASUREMENT);
        Matcher matcher = pattern.matcher(measurement);

        if (matcher.matches()) {
            try {
                String room = matcher.group(1);
                String type = matcher.group(2);
                int value = Integer.parseInt(matcher.group(3));

                ClimateType climateType = ClimateType.valueOf(type);
                return new ClimateMeasurement(room, climateType, value);
            } catch (Exception e) {
                throw new InvalidMeasurement();
            }
        } else {
            throw new InvalidMeasurement();
        }
    }
}
