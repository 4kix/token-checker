package com.iba.tokenchecker.fwk.exception;

public class JwtTokenMissingException extends RuntimeException {

    public JwtTokenMissingException() {
    }

    public JwtTokenMissingException(String message) {
        super(message);
    }

    public JwtTokenMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtTokenMissingException(Throwable cause) {
        super(cause);
    }
}
