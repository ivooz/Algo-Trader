package com.gft.service;

/**
 * Created by iozi on 13/10/2015.
 */
public class DataAccessException extends Exception {

    public DataAccessException() {
    }

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAccessException(Throwable cause) {
        super(cause);
    }
}
