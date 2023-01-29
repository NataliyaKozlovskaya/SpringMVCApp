package org.example.controllers;

import org.example.models.Book;
import org.example.models.Person;
import org.example.services.BooksService;
import org.example.services.PeopleService;
import org.example.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final PeopleService peopleService;
    private final BooksService booksService;
    private final PersonValidator personValidator;

    @Autowired
    public PersonController(PeopleService peopleService, BooksService booksService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.booksService = booksService;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String getAll(Model model){
        List<Person> peopleList = peopleService.findAll();
        model.addAttribute("peopleList", peopleList);
        return "people/getAll";
    }


    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute ("person") @Valid Person person,
                         BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/new";
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Integer id){
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") Integer id){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/edit";
        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id){
        peopleService.delete(id);
        return "redirect:/people";
    }

    @GetMapping("/{personId}")
    public String getBookByPersonId(@PathVariable("personId") Integer personId, Model model){
        Person person = peopleService.findOne(personId);
        List<Book> books = booksService.findAllByPersonId(personId);
        person.setBooks(books);
        model.addAttribute("man", person);
        return "people/getById";
    }
}
