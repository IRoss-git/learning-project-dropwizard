package com.ilya.dropwizard.RowMapper;

import com.ilya.dropwizard.domain.Person;
import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

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
