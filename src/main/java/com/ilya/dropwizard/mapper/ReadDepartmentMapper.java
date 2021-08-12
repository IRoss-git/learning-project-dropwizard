package com.ilya.dropwizard.mapper;

import com.ilya.dropwizard.domain.Department;
import com.learn.dropwizard.model.ReadDepartmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReadDepartmentMapper implements BaseMapper <ReadDepartmentDTO, Department> {


    @Autowired
    private ReadPersonMapper readPersonMapper;

    @Override
    public Department convertToEntity(ReadDepartmentDTO input) {
        return null;
    }

    @Override
    public ReadDepartmentDTO convertToDto(Department input) {
        ReadDepartmentDTO readDepartmentDTO = new ReadDepartmentDTO();

        readDepartmentDTO.setId(input.getId());
        readDepartmentDTO.setName(input.getName());
        readDepartmentDTO.setLocation(input.getLocation());

        return readDepartmentDTO;
    }
}
