package org.example.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Book")
public class Book {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @NotEmpty(message="Title should not be empty")
    @Size(min=1, max=50, message="Title should be between 1 and 50 characters")
    private String title;

    @NotEmpty(message="Author's name should not be empty")
    @Size(min=1, max=30, message="Author's name should be between 1 and 30 characters")
    private String author;

    @Min(value = 1900, message = "Age should be bigger than 1900")
    private Integer year;

    @Column(name="data")
    private Date data;

    @ManyToOne
    @JoinColumn(name="personId", referencedColumnName = "id")
    private Person person;


}
