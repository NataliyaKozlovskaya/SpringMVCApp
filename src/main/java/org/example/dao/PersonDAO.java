package org.example.dao;

import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAll() {
       return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }
    public Optional<Person> getByFullName(String fullName) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE fullName=?", new Object[]{fullName}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }
    public Person getById(int personId) {
                return jdbcTemplate.query("SELECT * FROM Person WHERE personId=?", new Object[]{personId}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
       jdbcTemplate.update("INSERT INTO Person(fullName, yearOfBirth) values (?,?)", person.getFullName(), person.getYearOfBirth());
    }

    public void update(int personId, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET fullName=?, yearOfBirth=? WHERE personId=?",
                updatedPerson.getFullName(), updatedPerson.getYearOfBirth(), personId);
    }
    public void delete(int personId) {
        jdbcTemplate.update("DELETE FROM Person WHERE personId=?", personId);
    }



}

