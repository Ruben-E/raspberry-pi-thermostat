package nl.rubenernst.projects.thermostat.processor.configuration.mqtt;

import nl.rubenernst.projects.thermostat.processor.handler.MeasurementHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class MqttConfiguration {
    public static final String MEASUREMENTS_TOPIC = "measurements";
    public static final String MANUAL_PREFERRED_TEMPERATURES_TOPIC = "manual_preferred_temperatures";
    public static final String RADIATOR_STATUSES_TOPIC = "radiator_statuses";
    public static final String MQTT_URL = "tcp://192.168.2.252:1883";
    public static final String CLIENT_ID = "thermostat";

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(MQTT_URL, CLIENT_ID, MEASUREMENTS_TOPIC, MANUAL_PREFERRED_TEMPERATURES_TOPIC);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler(MeasurementHandler measurementHandler) {
        return measurementHandler;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler outbound(MqttPahoClientFactory mqttClientFactory) {
        MqttPahoMessageHandler messageHandler =
                new MqttPahoMessageHandler("testClient", mqttClientFactory);
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(RADIATOR_STATUSES_TOPIC);
        return messageHandler;
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory(@Value("mqtt.uri") String mqttUri) {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setServerURIs(mqttUri);
        return factory;
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }
//
//    @Bean
//    public Gateway gateway(Gateway gateway) {
//        return gateway;
//    }

    @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
    public interface Gateway {
        void sendToMqtt(String data);
    }
}
