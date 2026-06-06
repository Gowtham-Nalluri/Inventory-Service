package com.hcl.inventory.exception;

import java.util.List;

public class ValidationException
        extends RuntimeException {

    private final List<String> errors;

    public ValidationException(
            List<String> errors) {

        super("Validation Failed");

        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}