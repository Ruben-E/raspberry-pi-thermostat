package nl.rubenernst.projects.thermostat.processor.handler;

import nl.rubenernst.projects.thermostat.processor.domain.ClimateMeasurement;
import nl.rubenernst.projects.thermostat.processor.domain.ClimateType;
import nl.rubenernst.projects.thermostat.processor.exceptions.InvalidPayload;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ClimateMeasurementMapper {
//    private static final String VALID_MEASUREMENT = "^\\|(.+)\\|(.+)\\|(\\d{4})\\|$";
    private static final String VALID_MEASUREMENT = "^\\|(.+)\\|(.+)\\|$";

    public static ClimateMeasurement convertToClimateMeasurement(String measurement) throws InvalidPayload {
        Pattern pattern = Pattern.compile(VALID_MEASUREMENT);
        Matcher matcher = pattern.matcher(measurement);

        if (matcher.matches()) {
            try {
                String temperatureString = matcher.group(1);
                String strippedTemperatureString = temperatureString.replace(".", "");
                int temperature = Integer.valueOf(strippedTemperatureString);

                // Ignored for now
                String humidity = matcher.group(2);

                ClimateType climateType = ClimateType.TEMPERATURE;
                return new ClimateMeasurement(null, climateType, temperature);
            } catch (Exception e) {
                throw new InvalidPayload("Payload is valid: " + measurement);
            }
        } else {
            throw new InvalidPayload("Payload is valid: " + measurement);
        }
    }
}
