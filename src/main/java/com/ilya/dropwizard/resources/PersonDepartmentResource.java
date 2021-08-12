package com.ilya.dropwizard.resources;

import java.util.List;

import com.ilya.dropwizard.service.PersonService;
import com.learn.dropwizard.model.AddPersonToDepartmentDTO;
import com.learn.dropwizard.model.ReadPersonDTO;
import com.learn.dropwizard.api.DepartmentApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;

@Component
public class PersonDepartmentResource implements DepartmentApi {

    @Autowired
    private PersonService personService;

    @Override
    public Response deletePersonFromDepartment(Long departmentId, Long id) {
        personService.deletePersonFromDepartment(departmentId,id);

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Override
    public Response getAllPersonsByDepartment(Long departmentId, Long pageNumber, Long pageSize) {
        List <ReadPersonDTO> personDTOList = personService.getAllPersonsByDepartment(departmentId, pageNumber, pageSize);

        return Response.status(Response.Status.OK).entity(personDTOList).build();
    }

    @Override
    public Response getPersonByDepartment(Long departmentId, Long id) {
        ReadPersonDTO readPersonDTO= personService.getPersonByDepartmentIdAndId(departmentId, id);

        return Response.status(Response.Status.OK).entity(readPersonDTO).build();
    }

    @Override
    public Response setPersonForDepartment(Long departmentId, AddPersonToDepartmentDTO addPersonToDepartmentDTO) {
        personService.addPersonToDepartment(departmentId, addPersonToDepartmentDTO);
        return Response.status(Response.Status.CREATED).build();
    }

}
