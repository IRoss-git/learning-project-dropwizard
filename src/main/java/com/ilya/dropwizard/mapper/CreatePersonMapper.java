package com.ilya.dropwizard.mapper;

import com.ilya.dropwizard.domain.Person;
import com.learn.dropwizard.model.CreateUpdatePersonDTO;
import org.springframework.stereotype.Component;

@Component
public class CreatePersonMapper implements BaseMapper <CreateUpdatePersonDTO, Person> {

    @Override
    public Person convertToEntity(CreateUpdatePersonDTO input) {
        Person person = new Person();

        person.setName(input.getName());
        person.setSurname(input.getSurname());
        person.setEmail(input.getEmail());
        person.setPassword(input.getPassword());

        return person;
    }

    @Override
    public CreateUpdatePersonDTO convertToDto(Person input) {
        throw new UnsupportedOperationException("Converter not supported");
    }
}
