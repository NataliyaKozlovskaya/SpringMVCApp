package org.example.controllers;

import org.example.dao.BookDAO;
import org.example.dao.PersonDAO;
import org.example.models.Book;
import org.example.models.Person;
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

    private final PersonDAO personDAO;
    private final BookDAO bookDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PersonController(PersonDAO personDAO, BookDAO bookDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String getAll(Model model){
// получим всех людей из дао и передадим на отображение в представление
        List<Person> peopleList = personDAO.getAll();
        model.addAttribute("peopleList", peopleList);
        return "people/getAll";
    }

//    @GetMapping("/{personId}")
//    public String getByIdContr(@PathVariable("personId") int personId, Model model){
//// получим одного человка по id из дао и передадим на отображение в представление
//        model.addAttribute("person", personDAO.getById(personId));
//        return  "people/getById";
//    }

    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute ("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/new";
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{personId}/edit")
    public String edit(Model model, @PathVariable("personId") int personId){
        model.addAttribute("person", personDAO.getById(personId));
        return "people/edit";
    }

    @PatchMapping("/{personId}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("personId") int personId){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/edit";
        personDAO.update(personId, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{personId}")
    public String delete(@PathVariable("personId") int personId){
        personDAO.delete(personId);
        return "redirect:/people";
    }

    @GetMapping("/{personId}")
    public String getBookByPersonId(@PathVariable("personId") int personId, Model model){
        Person person = personDAO.getById(personId);
        List<Book> books = bookDAO.getByPersonId(personId);
        person.setBooks(books);
        model.addAttribute("man", person);
        return "people/getById";
    }
}
