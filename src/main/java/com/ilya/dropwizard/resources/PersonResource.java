package com.ilya.dropwizard.resources;

import com.ilya.dropwizard.service.vault.VaultService;
import com.learn.dropwizard.api.PersonsApi;
import com.learn.dropwizard.model.CreateContentDTO;
import com.learn.dropwizard.model.CreateUpdatePersonDTO;
import com.learn.dropwizard.model.ReadPersonDTO;
import com.learn.dropwizard.model.ReadPersonsDTO;
import com.learn.dropwizard.model.ReadContentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ilya.dropwizard.service.PersonService;

import java.util.List;


import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource implements PersonsApi {

    @Autowired
    private PersonService personService;

    @Autowired
    private VaultService vaultService;

    @Override
    public Response createContent(CreateContentDTO createContentDTO) {
        vaultService.write(createContentDTO);

        return Response.status(Response.Status.CREATED).entity(createContentDTO).build();
    }

    @Override
    public Response getContent(String key) {
        ReadContentDTO readContentDTO = vaultService.read(key);

        return Response.status(Response.Status.OK).entity(readContentDTO).build();
    }

    @Override
    public Response createPerson(CreateUpdatePersonDTO createUpdatePersonDTO) {
        ReadPersonDTO readPersonDTO = personService.createPerson(createUpdatePersonDTO);

        return Response.status(Response.Status.CREATED).entity(readPersonDTO).build();
    }

    @Override
    public Response deletePerson(Long id) {
        personService.deletePerson(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Override
    public Response getPerson(Long id) {
        ReadPersonDTO readPersonDTO = personService.getPerson(id);

        return Response.status(Response.Status.OK).entity(readPersonDTO).build();
    }

    @Override
    public Response getPersons(Long pageNumber, Long pageSize) {
        List<ReadPersonsDTO> readPersonsDTOList = personService.getAllPersons(pageNumber, pageSize);

        return Response.status(Response.Status.OK).entity(readPersonsDTOList).build();
    }

    @Override
    public Response updatePerson(Long id, CreateUpdatePersonDTO createUpdatePersonDTO) {
        ReadPersonDTO readPersonDTO = personService.updatePerson(id, createUpdatePersonDTO);

        return Response.status(Response.Status.OK).entity(readPersonDTO).build();
    }
}
