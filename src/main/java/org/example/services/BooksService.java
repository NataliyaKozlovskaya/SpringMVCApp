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
    public void setBookForPerson(Integer id, Integer personId) {
        Book book = booksRepository.getOne(id);
        Person person = peopleRepository.getOne(personId);
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
    //    public Page<Book> findAll(Pageable pageable) {
//        return null;
    public Page<Book> findPage(Integer page, Integer bookPerPage){
        Pageable pageable = PageRequest.of(page, bookPerPage);
        return booksRepository.findAll(pageable);
    }


    // ПОИСК ВСЕХ НАЗВАНИЙ КНИГ
//   /

    //СЧИТАЕМ КОЛ-ВО СТРАНИЦ

    public long findCount(){
        return booksRepository.count();
    }

}
