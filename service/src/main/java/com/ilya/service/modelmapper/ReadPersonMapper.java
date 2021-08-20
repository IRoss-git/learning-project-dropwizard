package com.ilya.service.modelmapper;

import com.learn.dropwizard.model.ReadPersonDTO;
import com.ilya.db.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReadPersonMapper implements BaseMapper<ReadPersonDTO, Person> {

    @Autowired
    ReadDepartmentMapper readDepartmentMapper;

    @Override
    public Person convertToEntity(ReadPersonDTO input) {
        throw new UnsupportedOperationException("Converter not supported");
    }

    @Override
    public ReadPersonDTO convertToDto(Person input) {
        ReadPersonDTO person = new ReadPersonDTO();

        person.setId(input.getId());
        person.setName(input.getName());
        person.setSurname(input.getSurname());
        person.setEmail(input.getEmail());

        if(input.getDepartments()!=null) {
            person.setDepartments(readDepartmentMapper.convertListToDto(input.getDepartments()));
        }
        return person;
    }
}
