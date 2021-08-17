package com.ilya.dropwizard.service;

import com.ilya.dropwizard.dao.DepartmentDAO;
import com.ilya.dropwizard.domain.Department;
import com.ilya.dropwizard.mapper.CreateDepartmentMapper;
import com.ilya.dropwizard.mapper.ReadDepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.learn.dropwizard.model.ReadDepartmentDTO;
import com.learn.dropwizard.model.CreateUpdateDepartmentDTO;

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


        departmentDAO.insertDepartment(department);
        return readDepartmentMapper.convertToDto(department);

    }

    @Override
    public List<ReadDepartmentDTO> getAllDepartments(Long pageNumber, Long pageSize) {

        return readDepartmentMapper.convertListToDto(departmentDAO.getAllDepartments(pageNumber,pageSize));
    }

    @Override
    public List <Department> getAllDepartmentsByPerson(Long id){
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
