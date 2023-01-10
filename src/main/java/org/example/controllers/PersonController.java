package org.example.controllers;

import org.example.dao.PersonDAO;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonDAO personDAO;
    @Autowired
    public PersonController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping
    public String index(Model model){
// получим всех людей из дао и передадим на отображение в представление
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }
    @GetMapping("/{personId}")
    public String show(@PathVariable("personId") int personId, Model model){
// получим одного человка по id из дао и передадим на отображение в представление
        model.addAttribute("person", personDAO.show(personId));
        return  "people/show";
    }
    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "people/new";
    }
    @PostMapping
    public String create(@ModelAttribute ("person") @Valid Person person, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "people/new";
        personDAO.save(person);
        return "redirect: /people";
    }
    @GetMapping("/{personId}/edit")
    public String edit(Model model, @PathVariable("personId") int personId){
        model.addAttribute("person", personDAO.show(personId));
        return "people/edit";
    }
    @PatchMapping("/{personId}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("personId") int personId){
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

}
