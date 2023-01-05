package org.example.dao;

import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.beans.BeanProperty;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
       return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person){
        jdbcTemplate.update("INSERT INTO Person(name, email, age) VALUES(?,?,?)", person.getName(), person.getEmail(), person.getAge());
    }

    public void update(int id, Person updatedPerson) {
       jdbcTemplate.update("UPDATE Person SET name=?, email=?, age=? WHERE id=?", updatedPerson.getName(), updatedPerson.getEmail(), updatedPerson.getAge(), id);
    }
    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE id=?",id);
    }


///////////////
///// Тестируем производительность пакетной вставки Batch update
////////////////

    public void testMultipleUpdate(){
        List<Person> people = create1000People();
        long before = System.currentTimeMillis();
        for (Person person: people) {
        jdbcTemplate.update("INSERT INTO Person VALUES(?,?,?,?)", person.getId(), person.getName(), person.getEmail(), person.getAge());
        }
        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after-before));
    }

    public void testBatchUpdate(){
       List<Person>people=create1000People();
       long before=System.currentTimeMillis();
       jdbcTemplate.batchUpdate("INSERT INTO Person VALUES(?,?,?,?)", new BatchPreparedStatementSetter() {
           @Override
           public void setValues(PreparedStatement ps, int i) throws SQLException {
               ps.setInt(1, people.get(i).getId());
               ps.setString(2, people.get(i).getName());
               ps.setString(3, people.get(i).getEmail());
               ps.setInt(4, people.get(i).getAge());
           }
           @Override
           public int getBatchSize() {
               return people.size();
           }
       });

        long after=System.currentTimeMillis();
        System.out.println("Time: "+(after-before));
    }

    private List<Person> create1000People() {
        List<Person>people = new ArrayList<>();
        for (int i=0; i<1000; i++){
            people.add(new Person(i, "Name: "+ i, "test@ "+i, 30));
        }
        return people;
    }
}

