package org.example.controllers;

import org.example.dao.BookDAO;
import org.example.models.Book;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    @Autowired
    public BookController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }
    @GetMapping
    public String getAllBooksContr(Model model){
        List<Book> allBooks = bookDAO.getAllBooks();
        model.addAttribute("books", allBooks);
        return "books/getAllBooks";
    }
    @GetMapping("/{bookId}")
    public String getBookByIdContr(@PathVariable("bookId") int bookId, Model model){
        model.addAttribute("book", bookDAO.getById(bookId));
        return "/getBookById";
    }

    @GetMapping("/{personId}")
    public String getBookByPersonId(@PathVariable("personId") int personId, Model model){
        List<Book> allBookByPersonId = bookDAO.getByPersonId(personId);
        model.addAttribute("allBooks", allBookByPersonId);
        return "redirect:/people/getById";
    }

    @PostMapping("/newBook")
    public String createNewBookContr(@ModelAttribute("book") Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "people/new";
        bookDAO.saveNewBook(book);
        return "redirect:/books";
    }

    @PatchMapping("/{bookId}")
    public String update(@ModelAttribute("book") Book book, BindingResult bindingResult, @PathVariable("bookId") int bookId){
        if (bindingResult.hasErrors())
            return "/bookEdit";
        bookDAO.updateBook(bookId, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable("bookId") int bookId){
        bookDAO.deleteBook(bookId);
        return "redirect:/books";
    }
}
