package org.example.models;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Integer bookId;
    private Integer personId;
    @NotEmpty(message="Title should not be empty")
    @Size(min=1, max=50, message="Title should be between 1 and 50 characters")
    private String title;
    @NotEmpty(message="Author's name should not be empty")
    @Size(min=1, max=30, message="Author's name should be between 1 and 30 characters")
    private String author;
    private Integer year;
}
