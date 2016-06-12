package com.sales.ws.exception;

/**
 * User : Kamal Hossain
 * Date : 6/11/16.
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String msg) {
        super(msg);
    }

    public ValidationException(final String message, final Exception e) {
        super(message, e);
    }
}
