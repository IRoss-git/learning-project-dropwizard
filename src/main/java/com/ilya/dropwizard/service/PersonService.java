package com.ilya.dropwizard.service;

import com.ilya.dropwizard.dto.PersonDto;

import java.util.List;

public interface PersonService {

    PersonDto createPerson(PersonDto personDto);

    List<PersonDto> getAllPersons(int pageNumber, int pageSize);

    void deletePerson(Integer id);

    void updatePerson(Integer id, PersonDto personDto);

    PersonDto getPerson(Integer id);
}
