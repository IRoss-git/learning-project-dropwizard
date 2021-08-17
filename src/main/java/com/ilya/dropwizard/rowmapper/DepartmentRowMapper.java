package com.ilya.dropwizard.rowmapper;

import com.ilya.dropwizard.domain.Department;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DepartmentRowMapper implements RowMapper<Department> {



    @Override
    public Department mapRow(ResultSet rs, int i) throws SQLException {
        Department department = new Department();

        department.setId(rs.getLong("id"));
        department.setName(rs.getString("name"));
        department.setLocation(rs.getString("location"));

        return department;
    }
}
