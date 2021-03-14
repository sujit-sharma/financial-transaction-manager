package com.sujit.reconciliationwebapp.exception;

import lombok.*;

import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class ApiError {
    private String field;
    private String message;

    public static <T> Set<ApiError> create(Set<ConstraintViolation<T>> violations) {
        return violations.stream().map(e -> new ApiError(e.getPropertyPath().toString(),e.getMessage())).collect(Collectors.toSet());
    }
    public static void raiseAllIfNecessary(Set<ApiError> errors) {
        if (errors.isEmpty()) return;
        throw new ViolationException(errors);
    }
}
