package org.example.services;

import org.example.models.Book;
import org.example.models.Person;
import org.example.repositories.BooksRepository;
import org.example.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional(readOnly = true)
@Service
public class BooksService {

    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findPage() {
        return booksRepository.findAll();
    }

    @Transactional
    public void save(Integer id, Book updateBook) {
        updateBook.setId(id);
        booksRepository.save(updateBook);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    public Book findById(Integer id) {
        Optional<Book> book = booksRepository.findById(id);
        return book.orElse(null);
    }

    public List<Book> findAllByPersonId(Integer personId) {
        return booksRepository.findAllByPersonId(personId);
    }

    @Transactional
    public void setBookForPerson(Integer id, Person person) {
        Book book = booksRepository.getOne(id);
//        Person person = peopleRepository.getOne(person);
        book.setPerson(person);
        booksRepository.save(book);
    }

    @Transactional
    public void deletePersonFromBook(Integer id) {
        Book book = booksRepository.getOne(id);
        book.setPerson(null);
        booksRepository.save(book);
    }

    @Transactional
    public void deleteById(Integer id) {
        booksRepository.deleteById(id);
    }


    //ПАГИНАЦИЯ
    public Page<Book> findPage(Integer page, Integer bookPerPage){
        Pageable pageable = PageRequest.of(page, bookPerPage);
        return booksRepository.findAll(pageable);
    }

    //СЧИТАЕМ КОЛ-ВО СТРАНИЦ
    public long findCount(){
        return booksRepository.count();
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

    public List<Book> findByTitleIsContaining(String title){
        return booksRepository.findByTitleIsContaining(title);
    }
//    public List<Book> findAll(){
//        return booksRepository.findAll();
//    }
//    public Optional<String> findByBook(Optional<Book> book){
//        Optional<Person> person = Optional.ofNullable(book.get().getPerson());
//        return Optional.ofNullable(person.get().getFullName());
//    }
}

