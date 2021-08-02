package com.ilya.dropwizard.resources;

import com.ilya.dropwizard.DAO.PersonDAO;
import com.ilya.dropwizard.domain.Person;
import com.ilya.dropwizard.dto.PersonDto;
import com.learn.dropwizard.api.PersonsApi;
import com.learn.dropwizard.model.CreatePerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ilya.dropwizard.service.PersonService;
import java.util.List;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("persons")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource implements PersonsApi {

    @Autowired
    private PersonService personService;

    @POST
    public PersonDto addPerson(PersonDto person) {
        return personService.createPerson(person);
    }

    @GET
    @Path("/{id}")
    public PersonDto getById(@PathParam("id") int id) {
        return personService.getPerson(id);
    }

    @GET
    @Path("/query")
    public List<PersonDto> getAllPersons(@QueryParam("page_number") int pageNumber,
                                      @QueryParam("page_size") int pageSize) {
        return personService.getAllPersons(pageNumber, pageSize);
    }

    @PUT
    @Path("/{id}")
    public void updatePerson(@PathParam("id") int id, PersonDto personDto) {
        personService.updatePerson(id, personDto);
    }

    @DELETE
    @Path("/{id}")
    public void deletePerson(@PathParam("id") int id) {
        personService.getPerson(id);
    }

    @POST
    @Path("/ilya")
    @Override
    public Response createPerson(CreatePerson createPerson) {
        return null;
    }
}
