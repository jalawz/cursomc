package com.cursomc.service.exception;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException (final String msg) {
        super(msg);
    }

    public ObjectNotFoundException (final String msg, final Throwable cause) {
        super(msg, cause);
    }
}
