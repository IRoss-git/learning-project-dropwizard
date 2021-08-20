package com.ilya.service.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class AlreadyExistExceptionMapper implements ExceptionMapper<AlreadyExistException> {
    @Override
    public Response toResponse(AlreadyExistException e) {
        return Response.status(Response.Status.CONFLICT)
                .entity(e.getMessage())
                .build();
    }
}
