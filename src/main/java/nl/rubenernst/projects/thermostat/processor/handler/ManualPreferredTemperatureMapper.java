package nl.rubenernst.projects.thermostat.processor.handler;

import nl.rubenernst.projects.thermostat.processor.domain.ManualPreferredTemperature;
import nl.rubenernst.projects.thermostat.processor.exceptions.InvalidPayload;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ManualPreferredTemperatureMapper {
    private static final String VALID_PAYLOAD = "^\\|(.+)\\|(\\d{4})\\|$";

    public static ManualPreferredTemperature convertToManualPreferredTemperature(String payload) throws InvalidPayload {
        Pattern pattern = Pattern.compile(VALID_PAYLOAD);
        Matcher matcher = pattern.matcher(payload);

        if (matcher.matches()) {
            try {
                String room = matcher.group(1);
                int manualPreferredTemperature = Integer.parseInt(matcher.group(2));

                return new ManualPreferredTemperature(room, manualPreferredTemperature);
            } catch (Exception e) {
                throw new InvalidPayload("Payload is valid: " + payload);
            }
        } else {
            throw new InvalidPayload("Payload is valid: " + payload);
        }
    }
}
