package com.ilya.service.service;

import com.learn.dropwizard.model.CreateUpdateDepartmentDTO;
import com.learn.dropwizard.model.ReadDepartmentDTO;
import com.ilya.db.domain.Department;

import java.util.List;

public interface DepartmentService {

    ReadDepartmentDTO createDepartment(CreateUpdateDepartmentDTO createUpdateDepartmentDTO);

    List<ReadDepartmentDTO> getAllDepartments(Long pageNumber, Long pageSize);

    void deleteDepartment(Long id);

    ReadDepartmentDTO updateDepartment(Long id, CreateUpdateDepartmentDTO createUpdateDepartmentDTO);

    ReadDepartmentDTO getDepartment(Long id);

    List<Department> getAllDepartmentsByPerson(Long id);

    boolean isDepartmentExists(Long id);
}
