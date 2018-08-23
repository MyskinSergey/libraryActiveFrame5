package ru.intertrust.workfinder.exception;

/**
 * Created by Vitaliy Orlov on 20.12.2016.
 */
public class DomainObjectProcessingException extends RuntimeException {
    public DomainObjectProcessingException() {
        super();
    }

    public DomainObjectProcessingException(String message) {
        super(message);
    }

    public DomainObjectProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainObjectProcessingException(Throwable cause) {
        super(cause);
    }

    protected DomainObjectProcessingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
