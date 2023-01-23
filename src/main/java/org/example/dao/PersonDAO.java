package org.example.dao;

import org.example.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
public class PersonDAO {
    private final SessionFactory sessionFactory;

    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Transactional(readOnly = true)
    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession();
        List<Person> people = session.createQuery("SELECT p FROM Person p", Person.class).getResultList();
        return people;
    }
    @Transactional(readOnly = true)
    public Person show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id);
    }
    @Transactional
    public void save(Person person){
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }
    @Transactional
    public void update(int id, Person updatedPerson) {
        Session session = sessionFactory.getCurrentSession();
        Person personToBeUp = session.get(Person.class, id);
        personToBeUp.setName(updatedPerson.getName());
        personToBeUp.setAge(updatedPerson.getAge());
        personToBeUp.setEmail(updatedPerson.getEmail());
    }
    @Transactional
    public void delete(int id){
        Session session= sessionFactory.getCurrentSession();
        session.remove(session.get(Person.class, id));
    }
}

