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

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
        List<Book> allBooks = bookDAO.getAllBooks();
        model.addAttribute("allBooks", allBooks);
        return "books/getAllBooks";
    }
    @GetMapping("/{bookId}")
    public String getBookById(@PathVariable("bookId") Integer bookId, Model model, @ModelAttribute("person") Person person){
        Book bookById = bookDAO.getById(bookId);
        model.addAttribute("book", bookById);
        Optional<Person> bookOwner = bookDAO.getFullNameByBookId(bookId);
        if (bookOwner.isPresent()){
            model.addAttribute("owner", bookOwner.get());
        }else{
            model.addAttribute("peopleList", personDAO.getAll());
        }
        return "books/getBookById";

    }

    @PatchMapping("/{bookId}/addPerson")
    public String setBook(@ModelAttribute("personId") Integer personId, @PathVariable("bookId") Integer bookId){
        System.out.println("addPerson");
        bookDAO.setBookForPerson(personId, bookId);
        System.out.println("updated");
        return "redirect:/books/{bookId}";
    }

    @PatchMapping("/{bookId}/free")
    public String freeBook(@PathVariable("bookId") Integer bookId){
        System.out.println("free");
        bookDAO.deletePersonId(bookId);

        return "redirect:/books/{bookId}";
    }

    @GetMapping("/newBook")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "books/newBook";
    }

    @PostMapping
    public String createNewBook(@ModelAttribute("book") @Valid Book book,
                                BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "books/newBook";
        bookDAO.saveNewBook(book);
        return "redirect:/books";
    }

    @PatchMapping("/{bookId}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("bookId") Integer bookId){
        if (bindingResult.hasErrors())
            return "books/bookEdit";
        bookDAO.updateBook(book, bookId);
        return "redirect:/books";
    }

    @GetMapping("/{bookId}/bookEdit")
    public String edit(@PathVariable("bookId") Integer bookId, Model model){
        model.addAttribute("book", bookDAO.getById(bookId));
        return "books/bookEdit";
    }

    @DeleteMapping("/{bookId}")
    public String delete(@PathVariable("bookId") Integer bookId){
        bookDAO.delete(bookId);
        return "redirect:/books";
    }
}
