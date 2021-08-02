package com.ilya.dropwizard.service;

import com.ilya.dropwizard.DAO.PersonDAO;
import com.ilya.dropwizard.domain.Person;
import com.ilya.dropwizard.dto.PersonDto;
import com.ilya.dropwizard.exception.AlreadyExistException;
import com.ilya.dropwizard.mapper.PersonModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonModelMapper mapper;

    @Autowired
    private PersonDAO personDAO;

    @Override
    public PersonDto createPerson(PersonDto personDto) {
        Person person = mapper.convertToEntity(personDto);
        if (personDAO.isPersonExists(person.getEmail())) {
            throw new AlreadyExistException("Person with email:" + person.getEmail() + " already exists");
        }

        personDAO.insertPerson(person);
        return mapper.convertToDto(person);
    }

    @Override
    public List<PersonDto> getAllPersons(int pageNumber, int pageSize) {
        return mapper.convertListToDto(personDAO.getAllPersons(pageNumber, pageSize));
    }

    @Override
    public void deletePerson(Integer id) {
        personDAO.deletePersonById(id);
    }

    @Override
    public void updatePerson(Integer id, PersonDto personDto) {
        Person person = mapper.convertToEntity(personDto);

        personDAO.updatePerson(id,person);
    }

    @Override
    public PersonDto getPerson(Integer id) {
        if (!personDAO.isPersonExists(id)) {
            throw new NotFoundException("Person with id" + id + " not found");
        }
        return mapper.convertToDto(personDAO.getPersonById(id));
    }
}
