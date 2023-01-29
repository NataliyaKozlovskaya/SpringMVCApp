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

    //    ПАГИНАЦИЯ
//    @GetMapping("{page}&{bookPerPage}")
//    public String getAllBooks(@RequestParam("page") Integer page, @RequestParam("bookPerPage") Integer bookPerPage,
//                              Model model) {
////        page=1;
////        Integer defaultSize = 2;
////        long pageCount = booksService.findCount()/defaultSize;
//        long pageCount = booksService.findCount() / bookPerPage;
//        model.addAttribute("pageCount", pageCount);
//        model.addAttribute("page", booksService.findPage(page, bookPerPage));
//        booksService.findPage();
//        return "redirect:/books/getAllBooks";
//    }
//ПАГИНАЦИЯ
    @GetMapping
    public String getAllBooks(@RequestParam(value="page", required = false) Integer page,
                              @RequestParam(value="bookPerPage", required = false) Integer bookPerPage,
                              Model model) {
        if(page==null){
            page=1;
        }
        if(bookPerPage==null){
            bookPerPage= 1;
        }
        long countOfBooks = booksService.findCount();
        long pageCount = countOfBooks / bookPerPage;
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("page", booksService.findPage(page, bookPerPage));
        return "redirect:/books/getAllBooks";
    }


    @GetMapping("/{id}")
    public String getBookById(@PathVariable("id") Integer id, Model model, @ModelAttribute("person") Person person) {
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
    public String setBookForPerson(@PathVariable("id") Integer id, @ModelAttribute("personId") Integer personId) {
        booksService.setBookForPerson(id, personId);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/free")
    public String freeBook(@PathVariable("id") Integer id) {
        booksService.deletePersonFromBook(id);
        return "redirect:/books/{id}";
    }

    @GetMapping("/newBook")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/newBook";
    }

    @PostMapping
    public String createNewBook(@ModelAttribute("book") @Valid Book book,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/newBook";
        booksService.save(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") Integer id, @ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/bookEdit";
        booksService.save(id, book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/bookEdit")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("book", booksService.findById(id));
        return "books/bookEdit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        booksService.deleteById(id);
        return "redirect:/books";
    }


    // ПОИСК КНИГИ
//    @GetMapping("search")
//    public String search(@RequestParam{"title"} String title, Model model){
//        List<String> allTitle = booksService.findAllTitle();
//        //как сравнить??
//        List<String> books = allTitle.findByFirstnameLike(title);
//        if (books.isEmpty()){
//            model.addAttribute("allBooks", booksService.findAll());
//         }else {
//            model.addAttribute("books", books);
//        }
//
//    }

}
