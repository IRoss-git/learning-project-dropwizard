package com.ilya.db.dao;

import com.ilya.db.domain.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.ilya.db.rowmapper.DepartmentRowMapper;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class DepartmentDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DepartmentRowMapper departmentRowMapper;

    public Long insertDepartment(Department department) {
        String query = "INSERT INTO departments (name, location) VALUES (?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setString(1,department.getName());
            ps.setString(2,department.getLocation());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
//        jdbcTemplate.update(query, department.getName(), department.getLocation());
    }

    public Department getDepartmentById(Long id) {
        String query = "SELECT * FROM departments WHERE id = ?";
        return jdbcTemplate.queryForObject(query, departmentRowMapper, id);
    }

    public void deleteDepartmentById(Long id) {
        String query = "DELETE FROM departments WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    public List<Department> getAllDepartments(Long pageNumber, Long pageSize) {
        String query = "SELECT * FROM departments LIMIT ? OFFSET ?";

        return jdbcTemplate.query(
                query,
                departmentRowMapper, pageSize, pageSize * (pageNumber - 1));
    }

    public List <Department> getAllDepartmentByPerson(Long id){
        String query2 = "SELECT * FROM departments JOIN person_department ON departments.id = person_department.department_id WHERE person_department.person_id = ?";

        return jdbcTemplate.query(query2, new Object[]{id}, departmentRowMapper);
    }


    public void updateDepartment(Long id, Department department) {
        String query = "UPDATE departments SET name=?, location=? WHERE id = ? ";

        jdbcTemplate.update(query, department.getName(),
                department.getLocation(), id);
    }

    public boolean isDepartmentExists(Long id) {
        String query = "SELECT * FROM departments where id=?";
        List<Department> departments = jdbcTemplate.query(query, new Object[]{id}, departmentRowMapper);

        return departments.isEmpty();
    }

}
