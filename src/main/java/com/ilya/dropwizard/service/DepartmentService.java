package com.ilya.dropwizard.service;

import com.ilya.dropwizard.domain.Department;
import com.learn.dropwizard.model.ReadDepartmentDTO;
import com.learn.dropwizard.model.CreateUpdateDepartmentDTO;

import java.util.List;

public interface DepartmentService {

    ReadDepartmentDTO createDepartment(CreateUpdateDepartmentDTO createUpdateDepartmentDTO);

    List<ReadDepartmentDTO> getAllDepartments(Long pageNumber, Long pageSize);

    void deleteDepartment(Long id);

    ReadDepartmentDTO updateDepartment(Long id, CreateUpdateDepartmentDTO createUpdateDepartmentDTO);

    ReadDepartmentDTO getDepartment(Long id);
}
