package org.example.dao;

import org.example.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List <Book> getAllBooks(){
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public void updateBook(int bookId, Book updateBook){
        jdbcTemplate.update("UPDATE Book SET title=?, author=?, year=?",
                updateBook.getTitle(), updateBook.getAuthor(), updateBook.getYear(), bookId);
    }

    public void saveNewBook(Book book){
       jdbcTemplate.update("INSERT INTO Book (personId, title, author, year) VALUES(?, ?, ?, ?)",
               book.getPersonId(), book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void deleteBook(int bookId){
        jdbcTemplate.update("DELETE FROM Book WHERE bookId=?", bookId);
    }

    public Book getById(int bookId){
        return jdbcTemplate.query("SELECT * FROM Book WHERE bookId=?", new Object[]{bookId}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }
    public void setBookForPerson(int bookId, Book book){
        jdbcTemplate.update("UPDATE Book SET personId=? WHERE bookId=?", book.getPersonId(), bookId);
    }
}
