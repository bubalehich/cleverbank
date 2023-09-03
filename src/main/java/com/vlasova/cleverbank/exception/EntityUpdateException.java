package com.vlasova.cleverbank.exception;

public class EntityUpdateException extends RuntimeException{
    public EntityUpdateException() {
    }

    public EntityUpdateException(String message) {
        super(message);
    }

    public EntityUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityUpdateException(Throwable cause) {
        super(cause);
    }

    public EntityUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
