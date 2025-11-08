package org.allergit.exception;

public class EntityDuplicatedException extends RuntimeException {
    public EntityDuplicatedException(String message) {
        super(message);
    }
}
