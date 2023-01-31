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

//ПАГИНАЦИЯ
    @GetMapping
    public String getAllBooks(Model model) {

        Integer page=0;
        Integer bookPerPage=2;

        long countOfBooks = booksService.findCount();

        Page<Book> books = booksService.findPage(page, bookPerPage);
        model.addAttribute("books", books);

        long pageCount = getPageCount(countOfBooks, bookPerPage);
        model.addAttribute("pageCount", pageCount);

        model.addAttribute("bookPerPage", bookPerPage);
        model.addAttribute("page", page);
        return "books/getAllBooks";
    }
    @GetMapping("/page={page}/bookPerPage={bookPerPage}")
    public String paginationPage(@RequestParam("page") Integer page,
                                 @RequestParam("bookPerPage") Integer bookPerPage,
                                 Model model){

        long countOfBooks = booksService.findCount();
        Page<Book> books = booksService.findPage(page, bookPerPage);
        model.addAttribute("books", books);

        long pageCount = getPageCount(countOfBooks, bookPerPage);
        model.addAttribute("pageCount", pageCount);
        return "books/getAllBooks";
    }


//@Controller
//public class BookController {
//
//    @Autowired
//    private BookService bookService;
//
//    @RequestMapping(value = "/listBooks", method = RequestMethod.GET)
//    public String listBooks(
//      Model model,
//      @RequestParam("page") Optional<Integer> page,
//      @RequestParam("size") Optional<Integer> size) {
//        int currentPage = page.orElse(1);
//        int pageSize = size.orElse(5);
//
//        Page<Book> bookPage = bookService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
//
//        model.addAttribute("bookPage", bookPage);
//
//        int totalPages = bookPage.getTotalPages();
//        if (totalPages > 0) {
//            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
//                .boxed()
//                .collect(Collectors.toList());
//            model.addAttribute("pageNumbers", pageNumbers);
//        }
//
//        return "listBooks.html";
//    }
//}

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
    public String setBookForPerson(@PathVariable("id") Integer id, @ModelAttribute("person") Person person) {
        booksService.setBookForPerson(id, person);
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

    public long getPageCount(long countOfBooks, Integer bookPerPage) {
        long pageCount;
        if (countOfBooks % bookPerPage == 0) {
            pageCount = countOfBooks / bookPerPage;
        } else {
            pageCount = countOfBooks / bookPerPage + 1;
        }
        return pageCount;
    }

    @GetMapping("/search")
    public String searchBook(@RequestParam("str") String str,  Model model) {
        String searchBook = booksService.findByTitleLike(str);
//        String book = booksService.findByTitle(title);
//        model.addAttribute("book", book);
//        Optional<Person> owner = Optional.ofNullable(book.getPerson());
        List<Book> bookList = booksService.findAll();
        model.addAttribute("bookList", bookList);
        if (!searchBook.isEmpty()){
            model.addAttribute("searchBook", searchBook);
//            model.addAttribute("owner", owner);
        }
        return "books/search";
    }


}
