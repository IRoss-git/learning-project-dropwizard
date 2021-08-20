package com.ilya.service.service;

import com.learn.dropwizard.model.AddPersonToDepartmentDTO;
import com.learn.dropwizard.model.CreateUpdatePersonDTO;
import com.learn.dropwizard.model.ReadPersonDTO;
import com.learn.dropwizard.model.ReadPersonsDTO;
import com.ilya.db.dao.PersonDAO;
import com.ilya.db.domain.Person;
import com.ilya.service.exception.AlreadyExistException;
import com.ilya.service.modelmapper.CreatePersonMapper;
import com.ilya.service.modelmapper.ReadAllPersonsMapper;
import com.ilya.service.modelmapper.ReadPersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private CreatePersonMapper createPersonMapper;

    @Autowired
    private ReadPersonMapper readPersonMapper;

    @Autowired
    private ReadAllPersonsMapper readAllPersonsMapper;

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private DepartmentService departmentService;

    private final String NOT_FOUND = "%s with id %d not found";

    @Override
    public ReadPersonDTO createPerson(CreateUpdatePersonDTO personDto) {
        Person person = createPersonMapper.convertToEntity(personDto);
        if (!personDAO.isPersonExistsByEmail(person.getEmail())) {
            throw new AlreadyExistException("Person with email:" + person.getEmail() + " already exists");
        }

        Long id = personDAO.insertPerson(person);
        person.setId(id);
        return readPersonMapper.convertToDto(person);
    }

    @Override
    public List<ReadPersonsDTO> getAllPersons(Long pageNumber, Long pageSize) {
        return readAllPersonsMapper.convertListToDto(personDAO.getAllPersons(pageNumber, pageSize));
    }

    @Override
    public void deletePerson(Long id) {
        if (personDAO.isPersonExistsById(id)) {
            throw new NotFoundException(String.format(NOT_FOUND,"Person",id));
        }
        personDAO.deletePersonById(id);
    }


    @Override
    public ReadPersonDTO updatePerson(Long id, CreateUpdatePersonDTO personDto) {
        if (personDAO.isPersonExistsById(id)) {
            throw new NotFoundException(String.format(NOT_FOUND,"Person",id));
        }

        Person person = createPersonMapper.convertToEntity(personDto);

        if(!personDAO.isPersonExistsByEmail(personDto.getEmail())){
            throw new AlreadyExistException("Email: "+personDto.getEmail()+ " already exists");
        }

        personDAO.updatePerson(id,person);

        return readPersonMapper.convertToDto(person);
    }

    @Override
    public ReadPersonDTO getPerson(Long id) {
        if (personDAO.isPersonExistsById(id)) {
            throw new NotFoundException(String.format(NOT_FOUND,"Person",id));
        }

        Person person = personDAO.getPersonById(id);
        person.setDepartments(departmentService.getAllDepartmentsByPerson(id));

        return readPersonMapper.convertToDto(person);
    }

    public ReadPersonDTO addPersonToDepartment(Long departmentId,AddPersonToDepartmentDTO addPersonToDepartmentDTO){
        Long personId = addPersonToDepartmentDTO.getPersonId();

        checkPersonAndDepartmentExistence(departmentId, personId);

        personDAO.createPersonInDepartment(departmentId, personId);

        return readPersonMapper.convertToDto(personDAO.getPersonById(personId));
    }

    @Override
    public List<ReadPersonDTO> getAllPersonsByDepartment(Long departmentId, Long pageNumber, Long pageSize) {
        return readPersonMapper.convertListToDto(personDAO.getAllPersonsByDepartment(departmentId,pageNumber, pageSize));
    }

    public ReadPersonDTO getPersonByDepartmentIdAndId(Long departmentId, Long personId){
        checkPersonAndDepartmentExistence(departmentId, personId);

        return readPersonMapper.convertToDto(personDAO.getPersonByIdAndDepartmentId(departmentId,personId));
    }

    @Override
    public void deletePersonFromDepartment(Long departmentId, Long personId) {
        checkPersonAndDepartmentExistence(departmentId, personId);

        personDAO.deletePersonFromDepartmentById(departmentId,personId);
    }

    private void checkPersonAndDepartmentExistence(Long departmentId, Long personId){
        if(personDAO.isPersonExistsById(personId)){
            throw new NotFoundException(String.format(NOT_FOUND,"Person",personId));
        }
        if(departmentService.isDepartmentExists(departmentId)){
            throw new NotFoundException(String.format(NOT_FOUND,"Department",departmentId));
        }
    }
}
