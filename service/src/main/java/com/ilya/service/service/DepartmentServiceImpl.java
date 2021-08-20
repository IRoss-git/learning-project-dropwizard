package com.ilya.service.service;

import com.ilya.service.modelmapper.CreateDepartmentMapper;
import com.ilya.service.modelmapper.ReadDepartmentMapper;
import com.learn.dropwizard.model.CreateUpdateDepartmentDTO;
import com.learn.dropwizard.model.ReadDepartmentDTO;
import com.ilya.db.dao.DepartmentDAO;
import com.ilya.db.domain.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    private DepartmentDAO departmentDAO;

    @Autowired
    private CreateDepartmentMapper createDepartmentMapper;

    @Autowired
    private ReadDepartmentMapper readDepartmentMapper;

    private final String NOT_FOUND = "%s with id %d not found";

    @Override
    public ReadDepartmentDTO createDepartment(CreateUpdateDepartmentDTO createUpdateDepartmentDTO) {
        Department department = createDepartmentMapper.convertToEntity(createUpdateDepartmentDTO);

        Long id = departmentDAO.insertDepartment(department);

        department.setId(id);
        return readDepartmentMapper.convertToDto(department);

    }

    @Override
    public List<ReadDepartmentDTO> getAllDepartments(Long pageNumber, Long pageSize) {

        return readDepartmentMapper.convertListToDto(departmentDAO.getAllDepartments(pageNumber,pageSize));
    }

    @Override
    public List<Department> getAllDepartmentsByPerson(Long id){
        return departmentDAO.getAllDepartmentByPerson(id);
    }

    @Override
    public boolean isDepartmentExists(Long id) {
        return departmentDAO.isDepartmentExists(id);
    }

    @Override
    public void deleteDepartment(Long id) {
        if(departmentDAO.isDepartmentExists(id)){
            throw new NotFoundException(String.format(NOT_FOUND,"Department",id));
        }
        departmentDAO.deleteDepartmentById(id);
    }

    @Override
    public ReadDepartmentDTO updateDepartment(Long id, CreateUpdateDepartmentDTO createUpdateDepartmentDTO) {
        if(departmentDAO.isDepartmentExists(id)){
            throw new NotFoundException(String.format(NOT_FOUND,"Department",id));
        }

        Department department = createDepartmentMapper.convertToEntity(createUpdateDepartmentDTO);

        departmentDAO.updateDepartment(id,department);

        return readDepartmentMapper.convertToDto(department);
    }

    @Override
    public ReadDepartmentDTO getDepartment(Long id) {
        if(departmentDAO.isDepartmentExists(id)){
            throw new NotFoundException(String.format(NOT_FOUND,"Department",id));
        }
        return readDepartmentMapper.convertToDto(departmentDAO.getDepartmentById(id));
    }
}
