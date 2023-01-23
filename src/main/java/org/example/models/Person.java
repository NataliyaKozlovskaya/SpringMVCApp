package org.example.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
@Entity
@Table(name="Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @NotEmpty(message="Name should not be empty")
    @Size(min=2, max=30, message="Name should be between 2 and 30 characters")
    @Column(name="name")
    private String name;

    @Min(value = 0, message = "Age should be greater than 0")
    @Column(name="age")
    private int age;

    @NotEmpty(message = "Email should not be empty")
    @Email
    @Column(name="email")
    private  String email;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
