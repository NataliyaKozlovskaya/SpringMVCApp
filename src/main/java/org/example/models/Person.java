package org.example.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class Person {
    private int personId;
    @NotEmpty(message="Name should not be empty")
    @Size(min=2, max=30, message="Name should be between 2 and 30 characters")
    private String fullName;

    @Min(value = 1900, message = "Age should be greater than 1900")
    private int yearOfBirth;
    private List<Person> people= new ArrayList<>();
    public List<Book> getBooks() {
        return books;
    }

    public Person(List<Person> people) {
        this.people = people;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    List<Book> books = new ArrayList<>();
    public Person() {
    }

    public Person(int personId, String fullName, int yearOfBirth) {
        this.personId = personId;
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return personId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
