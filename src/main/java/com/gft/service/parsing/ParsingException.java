package com.gft.service.parsing;

/**
 * Created by iozi on 14/10/2015.
 */
public class ParsingException extends Exception {

    public ParsingException() {
    }

    public ParsingException(String message) {
        super(message);
    }

    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParsingException(Throwable cause) {
        super(cause);
    }
}
