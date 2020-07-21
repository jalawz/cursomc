package com.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class ValidationError extends StandardError {

    private final List<FieldMessage> errors = new ArrayList<>();

    public ValidationError (final Integer status, final String msg, final Long timestamp) {
        super(status, msg, timestamp);
    }

    public void addError (final String fieldName, final String message) {
        errors.add(new FieldMessage(fieldName, message));
    }
}
