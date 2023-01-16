package org.example.dao;

import org.example.models.Book;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAllBooks(){
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public void updateBook(Book updateBook, Integer bookId){
        jdbcTemplate.update("UPDATE Book SET title=?, author=?, year=? WHERE bookId=?",
                updateBook.getTitle(), updateBook.getAuthor(), updateBook.getYear(), bookId);
    }

    public void saveNewBook(Book book){
       jdbcTemplate.update("INSERT INTO Book (title, author, year) VALUES(?, ?, ?)",
             book.getTitle(), book.getAuthor(), book.getYear());
    }


    public Book getById(Integer bookId){
        return jdbcTemplate.query("SELECT * FROM Book WHERE bookId=" + bookId, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public List<Book> getByPersonId(Integer personId){
        return jdbcTemplate.query("SELECT * FROM Book WHERE personId="+ personId,
                        new BeanPropertyRowMapper<>(Book.class));

    }
    public void setBookForPerson(Integer personId, Integer bookId){
        jdbcTemplate.update("UPDATE Book SET personId=? WHERE bookId=?", personId, bookId);
    }

    public Optional<Person> getFullNameByBookId(Integer bookId){
        return jdbcTemplate.query("SELECT Person.* FROM Book join Person ON Book.personId = Person.personId" +
                " WHERE bookId=?", new Object[]{bookId}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
    public void deletePersonId(Integer bookId){
        jdbcTemplate.update("UPDATE Book SET personId=null WHERE bookId=?", bookId);
    }
    public void delete(Integer bookId){
        jdbcTemplate.update("DELETE FROM Book WHERE bookId=?", bookId);
    }
}
