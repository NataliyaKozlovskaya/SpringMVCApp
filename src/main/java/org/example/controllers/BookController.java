package org.example.controllers;

import org.example.models.Book;
import org.example.models.Person;
import org.example.services.BooksService;
import org.example.services.PeopleService;
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
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String getAllBooks(Model model){
        List<Book> allBooks = booksService.findAll();
        model.addAttribute("allBooks", allBooks);
        return "books/getAllBooks";
    }
    @GetMapping("/{id}")
    public String getBookById(@PathVariable("id") Integer id, Model model, @ModelAttribute("person") Person person){
        Book book = booksService.findById(id);
        model.addAttribute("book", book);

        Optional<Person> owner = Optional.ofNullable(book.getPerson());
        if (owner.isPresent()) {
            model.addAttribute("owner", owner.get());
        }else {
            model.addAttribute("peopleList", peopleService.findAll());
        }
        return "books/getBookById";
    }

    @PatchMapping("/{id}/addPerson")
    public String setBookForPerson(@PathVariable("id") Integer id, @ModelAttribute("personId") Integer personId){
        booksService.setBookForPerson(id, personId);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/free")
    public String freeBook(@PathVariable("id") Integer id){
        booksService.deletePersonFromBook(id);
        return "redirect:/books/{id}";
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
        booksService.save(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") Integer id, @ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "books/bookEdit";
        booksService.save(id, book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/bookEdit")
    public String edit(@PathVariable("id") Integer id, Model model){
        model.addAttribute("book", booksService.findById(id));
        return "books/bookEdit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id){
        booksService.deleteById(id);
        return "redirect:/books";
    }
}
