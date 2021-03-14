package com.sujit.reconciliationwebapp.exception;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ViolationException extends RuntimeException {
    private final Set<ApiError> errors;

    public ViolationException(Exception ex, ApiError error) {
        super(ex);
        this.errors = new HashSet<>();
        this.errors.add(error);
    }
    public ViolationException(ApiError error) {
        this.errors = new HashSet<>();
        this.errors.add(error);
    }
    public ViolationException(Set<ApiError> errors) {
        this.errors = errors;
    }
}
