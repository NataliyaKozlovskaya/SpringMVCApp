package org.example.controllers;

import org.example.dao.BookDAO;
import org.example.dao.PersonDAO;
import org.example.models.Book;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }
    @GetMapping
    public String getAllBooks(Model model){
        List<Book> booksList = bookDAO.getAllBooks();
        model.addAttribute("bookAll", booksList);
        return "books/getAllBooks";
    }
    @GetMapping("/{bookId}")
    public String getBookById(@PathVariable("bookId") int bookId, Model model,@ModelAttribute("bookId") Book book){
        String fullNameByBookId = bookDAO.getFullNameByBookId(bookId);
        List<Person> all = personDAO.getAll();
        Book book1 = bookDAO.getById(bookId);

        model.addAttribute("fullNameP", fullNameByBookId);
        model.addAttribute("bookAll", book1);
        model.addAttribute("allPerson", all);
        return "books/getBookById";
    }

    @PatchMapping("/addPersonId")
    public String setBook(@ModelAttribute("personId") Book book, int personId){
        bookDAO.setBookForPerson(personId);
        return "books/getBookById";
    }

    @PatchMapping("/free")
    public String freeBook(@ModelAttribute("bookId") Book book, int bookId){
        bookDAO.deletePersonId(bookId);
        return "redirect:/books/getBookById";
    }

    @GetMapping("/newBook")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "books/newBook";
    }
    @PostMapping
    public String createNewBook(@ModelAttribute("book") Book book,
                                BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "books/newBook";
        bookDAO.saveNewBook(book);
        return "redirect:/books";
    }

    @PatchMapping("/{bookId}")
    public String update(@ModelAttribute("book") Book book, BindingResult bindingResult, @PathVariable("bookId") int bookId){
        if (bindingResult.hasErrors())
            return "books/bookEdit";
        bookDAO.updateBook(bookId, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable("bookId") int bookId){
        bookDAO.deleteBook(bookId);
        return "redirect:/books";
    }
}
