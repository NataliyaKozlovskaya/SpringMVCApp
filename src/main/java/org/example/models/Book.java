package org.example.models;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private int bookId;
    private int personId;
    private String title;
    private String author;
    private int year;
    List<Book> books = new ArrayList<>();

    public Book() {
    }

    public Book(int bookId, int personId, String title, String author, int year, List<Book> books) {
        this.bookId = bookId;
        this.personId = personId;
        this.title = title;
        this.author = author;
        this.year = year;
        this.books = books;
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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
