package org.example.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @NotEmpty(message="Name should not be empty")
    @Size(min=2, max=30, message="Name should be between 2 and 30 characters")
    @Column(name="fullName")
    private String fullName;

    @Min(message = " Date should be more than 1900", value = 1900)
    @Column(name="year_of_birth")
    private Integer yearOfBirth;

    @OneToMany(mappedBy= "person")
    private List<Book> books = new ArrayList<>();

}
