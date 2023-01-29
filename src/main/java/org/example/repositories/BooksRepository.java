package org.example.repositories;
import org.example.models.Book;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByPersonId(Integer personId);


//    List<String> findByFirstnameLike(String b);


    @Override
    Page<Book> findAll(Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM Book", nativeQuery = true)
    Integer findCount();

//    @Override
//    long count();
}
