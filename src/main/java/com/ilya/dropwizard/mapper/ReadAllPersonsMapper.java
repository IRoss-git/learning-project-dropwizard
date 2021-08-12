package com.ilya.dropwizard.mapper;

import com.ilya.dropwizard.domain.Person;
import com.learn.dropwizard.model.ReadPersonsDTO;
import org.springframework.stereotype.Component;

@Component
public class ReadAllPersonsMapper implements BaseMapper<ReadPersonsDTO, Person> {

    @Override
    public Person convertToEntity(ReadPersonsDTO input) {
        throw new UnsupportedOperationException("Unsupported converter");
    }

    @Override
    public ReadPersonsDTO convertToDto(Person input) {
        ReadPersonsDTO readPersonsDTO = new ReadPersonsDTO();
        readPersonsDTO.setId(input.getId());
        readPersonsDTO.setEmail(input.getEmail());
        readPersonsDTO.setName(input.getName());
        readPersonsDTO.setSurname(input.getSurname());

        return readPersonsDTO;
    }
}
