package com.ninos.repository;

import com.ninos.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {

    List<Book> findByTitle(String title);
    List<Book> findByTitleContaining(String title);
    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByTitleAndAuthor(String title, String author);


    List<Book> findByTitleAndAuthorAndIsbnContaining(String title, String author, String isbn);







}
