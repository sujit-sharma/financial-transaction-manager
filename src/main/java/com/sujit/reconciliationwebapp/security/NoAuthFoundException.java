package com.sujit.reconciliationwebapp.security;

import org.springframework.security.core.AuthenticationException;

public class NoAuthFoundException extends AuthenticationException {

    public NoAuthFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public NoAuthFoundException(String msg) {
        super(msg);
    }
}
