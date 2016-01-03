package nl.rubenernst.projects.thermostat.processor.exceptions;

public class RepositoryException extends Exception {
    public RepositoryException(Exception e) {
        super(e);
    }
}
