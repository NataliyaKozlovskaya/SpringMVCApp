package org.example.dao;


import org.example.models.Person;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PersonDAO {
    private final EntityManager entityManager;

    @Autowired
    public PersonDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Transactional(readOnly = true)
    public void testNPlus1(){
      Session session = entityManager.unwrap(Session.class);

//      List<Person> people = session.createQuery("SELECT p FROM Person p").getResultList();
//
//      for(Person person: people)
//          System.out.println("Person: " + person.getName() + "  has: " + person.getItems());

//        List <Person> people = session.createQuery("SELECT p FROM Person p LEFT JOIN FETCH p.items")
//                .getResultList();
//        for (Person person: people)
//            System.out.println("Person: " + person.getName() + "  has: " + person.getItems());

        Set<Person> people = new HashSet<Person>(session.createQuery("SELECT p FROM Person p LEFT JOIN FETCH p.items")
                .getResultList());
        for (Person person: people)
            System.out.println("Person: " + person.getName() + "  has: " + person.getItems());


    }




}
