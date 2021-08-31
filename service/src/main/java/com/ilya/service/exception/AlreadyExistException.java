package com.ilya.service.exception;

import javax.ws.rs.WebApplicationException;

public class AlreadyExistException extends WebApplicationException {
    public AlreadyExistException(String message) {
        super(message);
    }
}
