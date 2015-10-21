package com.gft.repository;

/**
 * Created by iozi on 14/10/2015.
 */
public class InsufficientDataException extends Exception {

    public InsufficientDataException() {
    }

    public InsufficientDataException(String message) {
        super(message);
    }

    public InsufficientDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientDataException(Throwable cause) {
        super(cause);
    }
}
