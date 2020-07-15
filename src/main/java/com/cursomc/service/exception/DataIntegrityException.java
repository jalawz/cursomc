package com.cursomc.service.exception;

public class DataIntegrityException extends RuntimeException {

    public DataIntegrityException (final String msg) {
        super(msg);
    }

    public DataIntegrityException (final String msg, final Throwable cause) {
        super(msg, cause);
    }
}
