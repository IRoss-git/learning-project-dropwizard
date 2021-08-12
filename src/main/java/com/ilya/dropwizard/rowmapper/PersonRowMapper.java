package com.ilya.dropwizard.rowmapper;

import com.ilya.dropwizard.domain.Person;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PersonRowMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet rs, int i) throws SQLException {
        Person person = new Person();

        person.setId(rs.getInt("id"));
        person.setEmail(rs.getString("email"));
        person.setPassword(rs.getString("password"));
        person.setName(rs.getString("name"));
        person.setSurname(rs.getString("surname"));

        return person;
    }
}
