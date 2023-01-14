package org.example.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class Book {
    private int bookId;
    private int personId;
    @NotEmpty(message="Title should not be empty")
    @Size(min=1, max=50, message="Title should be between 1 and 50 characters")
    private String title;
    @NotEmpty(message="Author's name should not be empty")
    @Size(min=1, max=30, message="Author's name should be between 1 and 30 characters")
    private String author;
    private int year;

    public Book() {
    }

    public Book(int bookId, int personId, String title, String author, int year, List<Book> books) {
        this.bookId = bookId;
        this.personId = personId;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", personId=" + personId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }
}
