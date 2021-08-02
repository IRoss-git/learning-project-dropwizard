package com.ilya.dropwizard.mapper;

import com.ilya.dropwizard.domain.Person;
import com.ilya.dropwizard.dto.PersonDto;
import org.springframework.stereotype.Component;

@Component
public class PersonModelMapper implements BaseMapper<PersonDto, Person> {
    @Override
    public Person convertToEntity(PersonDto input) {
        Person person = new Person();

        person.setName(input.getName());
        person.setSurname(input.getSurname());
        person.setEmail(input.getEmail());
        person.setPassword(input.getPassword());

        return person;
    }

    @Override
    public PersonDto convertToDto(Person input) {
        PersonDto personDto = new PersonDto();

        personDto.setName(input.getName());
        personDto.setSurname(input.getSurname());
        personDto.setEmail(input.getEmail());
//        personDto.setPassword(input.getPassword());

        return personDto;
    }
}
