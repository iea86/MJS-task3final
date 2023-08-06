package com.epam.esm.exception;

public class BusinessValidationException extends Exception {

    public BusinessValidationException() {
    }

    public BusinessValidationException(String message) {
        super(message);
    }


    public BusinessValidationException(Throwable cause) {
        super(cause);
    }

    public BusinessValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessValidationException(String message, Throwable cause, boolean enableSuppression,
                                       boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);

    }
}
