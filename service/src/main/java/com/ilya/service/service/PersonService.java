package com.ilya.service.service;

import com.learn.dropwizard.model.CreateUpdatePersonDTO;
import com.learn.dropwizard.model.ReadPersonDTO;
import com.learn.dropwizard.model.ReadPersonsDTO;

import java.util.List;

public interface PersonService {

    ReadPersonDTO createPerson(CreateUpdatePersonDTO personDto);

    List<ReadPersonsDTO> getAllPersons(Long pageNumber, Long pageSize);

    void deletePerson(Long id);

    ReadPersonDTO updatePerson(Long id, CreateUpdatePersonDTO personDto);

    ReadPersonDTO getPerson(Long id);

    ReadPersonDTO addPersonToDepartment(Long departmentId, com.learn.dropwizard.model.AddPersonToDepartmentDTO addPersonToDepartmentDTO);

    List <ReadPersonDTO> getAllPersonsByDepartment(Long departmentId, Long pageNumber, Long pageSize);

    ReadPersonDTO getPersonByDepartmentIdAndId(Long departmentId, Long personId);

    void deletePersonFromDepartment(Long departmentId, Long personId);
}
