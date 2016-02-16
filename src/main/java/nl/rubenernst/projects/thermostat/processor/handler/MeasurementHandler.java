package nl.rubenernst.projects.thermostat.processor.handler;

import lombok.extern.slf4j.Slf4j;
import nl.rubenernst.projects.thermostat.processor.configuration.mqtt.MqttConfiguration;
import nl.rubenernst.projects.thermostat.processor.domain.ClimateMeasurement;
import nl.rubenernst.projects.thermostat.processor.domain.ManualPreferredTemperature;
import nl.rubenernst.projects.thermostat.processor.exceptions.InvalidPayload;
import nl.rubenernst.projects.thermostat.processor.service.ThermostatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MeasurementHandler implements MessageHandler {
    @Autowired
    private ThermostatService thermostatService;

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        String topic = (String) message.getHeaders().get("mqtt_topic");
        switch (topic) {
            case MqttConfiguration.MEASUREMENTS_TOPIC:
                handleMeasurementMessage(message);
                break;
            case MqttConfiguration.MANUAL_PREFERRED_TEMPERATURES_TOPIC:
                handleManualPreferredTemperatureMessage(message);
                break;
        }
    }

    private void handleManualPreferredTemperatureMessage(Message<?> message) {
        String payload = (String) message.getPayload();

        try {
            ManualPreferredTemperature manualPreferredTemperature = ManualPreferredTemperatureMapper.convertToManualPreferredTemperature(payload);
            thermostatService.handleManualPreferredTemperature(manualPreferredTemperature);
        } catch (InvalidPayload invalidPayload) {
            log.warn("Invalid payload: " + payload);

            throw new MessagingException("Invalid payload: " + payload, invalidPayload);
        }
    }

    private void handleMeasurementMessage(Message<?> message) {
        String payload = (String) message.getPayload();

        try {
            ClimateMeasurement climateMeasurement = ClimateMeasurementMapper.convertToClimateMeasurement(payload);
            thermostatService.handleMeasurement(climateMeasurement);
        } catch (InvalidPayload invalidPayload) {
            log.warn("Invalid payload: " + payload);

            throw new MessagingException("Invalid payload: " + payload, invalidPayload);
        }
    }
}
