package com.ilya.dropwizard.DAO;

import com.ilya.dropwizard.RowMapper.PersonRowMapper;
import com.ilya.dropwizard.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class PersonDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // driver manager vs datasource
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/test_db", "root", "123");
    }

    public void insertPerson(Person person) {
        String query = "INSERT INTO persons (email, password, name, surname) VALUES (?,?,?,?)";

        jdbcTemplate.update(query, person.getEmail(), person.getPassword(), person.getName(), person.getSurname());
    }

    // row mapper result set -> entity

    public Person getPersonById(int id) {
        String query = "SELECT * FROM persons WHERE id = ?";
        return jdbcTemplate.queryForObject(query, new PersonRowMapper(), id);
    }

    public void deletePersonById(int id) {
        String query = "DELETE FROM persons WHERE id = ?";

        jdbcTemplate.update(query, id);
    }

    // email
    public boolean isPersonExists(String email) {
        String query = "SELECT * FROM persons where email=?";
        try {
            jdbcTemplate.queryForObject(query, Integer.class, email);
            return true;
        }
        catch (IncorrectResultSetColumnCountException e ) {
            return true;
        }
        catch (EmptyResultDataAccessException e ){
            return false;
        }
    }

    public boolean isPersonExists(Integer id) {
        String query = "SELECT * FROM persons where id=?";
        try {
            jdbcTemplate.queryForObject(query, Integer.class, id);
            return true;
        }
        catch (IncorrectResultSetColumnCountException e ) {
            return true;
        }
        catch (EmptyResultDataAccessException e ){
            return false;
        }
    }

    public List <Person> getAllPersons(int pageNumber, int pageSize){
        String query = "SELECT * FROM persons LIMIT ? OFFSET ?";

        List<Person> personList = jdbcTemplate.query(
                query,
                new PersonRowMapper(), pageSize, pageSize * (pageNumber-1));

        return personList;
    }

    public void updatePerson(int id, Person person) {
        String query = "UPDATE persons SET email=?, password=? WHERE id = ? ";

        jdbcTemplate.update(query, person.getEmail(),
                person.getPassword(), id);
    }
}
