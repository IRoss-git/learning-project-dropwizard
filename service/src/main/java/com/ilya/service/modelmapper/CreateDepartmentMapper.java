package com.ilya.service.modelmapper;

import com.learn.dropwizard.model.CreateUpdateDepartmentDTO;
import com.ilya.db.domain.Department;
import org.springframework.stereotype.Component;

@Component
public class CreateDepartmentMapper implements BaseMapper<CreateUpdateDepartmentDTO, Department> {
    @Override
    public Department convertToEntity(CreateUpdateDepartmentDTO input) {
        Department department = new Department();
        department.setName(input.getName());
        department.setLocation(input.getLocation());

        return department;
    }

    @Override
    public CreateUpdateDepartmentDTO convertToDto(Department input) {
        return null;
    }
}
