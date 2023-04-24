package org.example.controllers;

import org.example.models.Book;
import org.example.models.Person;
import org.example.services.BooksService;
import org.example.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
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

    public long getPageCount(long countOfBooks, Integer bookPerPage) {
        long pageCount;
        if (countOfBooks % bookPerPage == 0) {
            pageCount = countOfBooks / bookPerPage;
        } else {
            pageCount = countOfBooks / bookPerPage + 1;
        }
        return pageCount;
    }

    //ПАГИНАЦИЯ
    @GetMapping
    public String getAllBooks(@RequestParam(value = "page", required = false) Integer page,
                              @RequestParam(value = "bookPerPage", required = false) Integer bookPerPage,
                              Model model) {
        List<Book> books = booksService.findAll();
        model.addAttribute("books", books);
        for (Book book : books) {
            Date data = book.getData();
            long time = data.getTime();
            Date dateN = new Date();
            long timeN = dateN.getTime();
            System.out.println(timeN - time);
            if ((timeN - time) > 864000000) {
                model.addAttribute("data", data);
            }
            if (page != null & bookPerPage != null) {
                Page<Book> booksP = booksService.findPage(page, bookPerPage);
                model.addAttribute("booksP", booksP);

                long countOfBooks = booksService.findCount();
                long pageCount = getPageCount(countOfBooks, bookPerPage);
                model.addAttribute("pageCount", pageCount);
                }
        }
        return "books/getAllBooks";
    }


    @GetMapping("/{id}")
    public String getBookById(@PathVariable("id") Integer id, Model model,
                              @ModelAttribute("person") Person person) {
        Book book = booksService.findById(id);
        model.addAttribute("book", book);

        Optional<Person> owner = Optional.ofNullable(book.getPerson());
        if (owner.isPresent()) {
            model.addAttribute("owner", owner.get());
        } else {
            model.addAttribute("peopleList", peopleService.findAll());
        }
        return "books/getBookById";
    }




        @PatchMapping("/{id}/addPerson")
        public String setBookForPerson (@PathVariable("id") Integer id,
                                        @ModelAttribute("person") Person person){
            booksService.setBookForPerson(id, person);
            return "redirect:/books/{id}";
        }

        @PatchMapping("/{id}/free")
        public String freeBook (@PathVariable("id") Integer id){
            booksService.deletePersonFromBook(id);
            return "redirect:/books/{id}";
        }

        @GetMapping("/newBook")
        public String newBook (Model model){
            model.addAttribute("book", new Book());
            return "books/newBook";
        }

        @PostMapping
        public String createNewBook (@ModelAttribute("book") @Valid Book book,
                BindingResult bindingResult){
            if (bindingResult.hasErrors())
                return "books/newBook";
            booksService.save(book);
            return "redirect:/books";
        }

        @PatchMapping("/{id}")
        public String update (@PathVariable("id") Integer id,
                              @ModelAttribute("book") @Valid Book book,
                BindingResult bindingResult){
            if (bindingResult.hasErrors())
                return "books/bookEdit";
            booksService.save(id, book);
            return "redirect:/books";
        }

        @GetMapping("/{id}/bookEdit")
        public String edit (@PathVariable("id") Integer id, Model model){
            model.addAttribute("book", booksService.findById(id));
            return "books/bookEdit";
        }

        @DeleteMapping("/{id}")
        public String delete (@PathVariable("id") Integer id){
            booksService.deleteById(id);
            return "redirect:/books";
        }

        @GetMapping("/search")
        public String searchBook (@RequestParam(value = "title", required = false) Optional < String > title,
                                  Model model){
            if (title.isPresent()) {
                System.out.println(title.get());
                List<Book> books = booksService.findByTitleIsContaining(title.get());
                System.out.println("нашли");
                model.addAttribute("searchBook", books);

                for (Book book : books) {
                    //2023-01-19 00:00:00.0
                    Date data = book.getData();
                    long time = data.getTime();
                    Date dateN = new Date();
                    long timeN = dateN.getTime();
                    System.out.println(timeN - time);
                    if ((timeN - time) > 864000000) {
                        model.addAttribute("data", data);
                    }
                }
            }
            return "books/search";
        }
}