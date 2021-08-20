package com.ilya.db.dao;

import com.ilya.db.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.ilya.db.rowmapper.PersonRowMapper;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class PersonDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PersonRowMapper personRowMapper;

    public Long insertPerson(Person person) {
        String query = "INSERT INTO persons (email, password, name, surname) VALUES (?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setString(1,person.getEmail());
            ps.setString(2,person.getPassword());
            ps.setString(3,person.getName());
            ps.setString(4,person.getSurname());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
//        jdbcTemplate.update(query, person.getEmail(), person.getPassword(), person.getName(), person.getSurname());
    }

    public void deletePersonById(Long id) {
        String query = "DELETE FROM persons WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    public boolean isPersonExistsByEmail(String email) {
        String query = "SELECT * FROM persons where email=?";
        List<Person> person = jdbcTemplate.query(query, new Object[]{email}, personRowMapper);

        return person.isEmpty();
    }

    public boolean isPersonExistsById(Long id) {
        String query = "SELECT * FROM persons where id=?";
        List<Person> person = jdbcTemplate.query(query, new Object[]{id}, personRowMapper);

        return person.isEmpty();
    }

    public List<Person> getAllPersons(Long pageNumber, Long pageSize) {
        String query = "SELECT * FROM persons LIMIT ? OFFSET ?";

        return jdbcTemplate.query(
                query,
                personRowMapper, pageSize, pageSize * (pageNumber - 1));
    }

    public Person getPersonById(Long id) {
        String query1 = "SELECT * FROM persons WHERE id = ?";

        return jdbcTemplate.queryForObject(query1, personRowMapper, id);
    }

    public List<Person> getAllPersonsByDepartment(Long departmentId, Long pageNumber, Long pageSize) {
        String query = "SELECT * FROM persons JOIN person_department ON persons.id = person_department.person_id WHERE person_department.department_id = ? LIMIT ? OFFSET ?;";

        return jdbcTemplate.query(
                query,
                personRowMapper, departmentId, pageSize, pageSize * (pageNumber - 1));
    }

    public Person getPersonByIdAndDepartmentId(Long departmentId, Long personId) {
        String query = "SELECT * FROM persons JOIN person_department ON persons.id = person_department.person_id WHERE person_department.department_id = ? AND persons.id = ?;";
        return jdbcTemplate.queryForObject(query, personRowMapper, departmentId, personId);
    }

    public void updatePerson(Long id, Person person) {
        String query = "UPDATE persons SET email=?, password=?, name=?, surname=? WHERE id = ? ";

        jdbcTemplate.update(query, person.getEmail(),
                person.getPassword(), person.getName(), person.getSurname(), id);
    }

    public void deletePersonFromDepartmentById(Long departmentId, Long personId) {
        String query = "DELETE FROM person_department WHERE person_department.department_id = ? AND person_department.person_id = ?";
        jdbcTemplate.update(query, departmentId, personId);
    }

    public void createPersonInDepartment(Long departmentId, Long personId) {
        String query = "INSERT INTO person_department VALUES (?,?)";
        jdbcTemplate.update(query, departmentId, personId);

    }
}
