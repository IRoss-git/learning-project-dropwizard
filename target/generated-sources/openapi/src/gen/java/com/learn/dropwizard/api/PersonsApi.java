package com.learn.dropwizard.api;

import com.learn.dropwizard.model.ApiError;
import com.learn.dropwizard.model.CreatePerson;
import com.learn.dropwizard.model.ReadPerson;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;


import java.io.InputStream;
import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/persons")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2021-08-02T18:39:59.630914900+03:00[Europe/Moscow]")public interface PersonsApi {

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Response createPerson(@Valid CreatePerson createPerson);
}
