package com.ilya.service.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AlreadyExistExceptionMapper implements ExceptionMapper<AlreadyExistException> {
    @Override
    public Response toResponse(AlreadyExistException e) {
        return Response.status(Response.Status.CONFLICT)
                .type(MediaType.APPLICATION_JSON)
                .entity(new BasicExceptionResponse(Response.Status.CONFLICT.getStatusCode(),e.getMessage()))
                .build();
    }
}
