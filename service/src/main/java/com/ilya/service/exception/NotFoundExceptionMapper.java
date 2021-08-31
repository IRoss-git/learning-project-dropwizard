package com.ilya.service.exception;

import com.learn.dropwizard.model.ApiErrorDTO;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ApiErrorDTO().responseCode(Response.Status.NOT_FOUND.getStatusCode()).errorMessage(e.getMessage()))
                .build();
    }
}
