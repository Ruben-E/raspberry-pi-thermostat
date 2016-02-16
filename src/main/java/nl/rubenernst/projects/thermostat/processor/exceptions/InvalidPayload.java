package nl.rubenernst.projects.thermostat.processor.exceptions;

public class InvalidPayload extends Exception {
    public InvalidPayload(String message) {
        super(message);
    }
}
