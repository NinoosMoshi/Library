package com.ninos.service;

import com.ninos.dto.BookDTO;

import java.util.List;

public interface BookService {

    BookDTO addBook(BookDTO bookDTO);

    List<BookDTO> getAllBooks();

    BookDTO getBookById(Long bookId);

    BookDTO updateBook(BookDTO bookDTO);

    void deleteBookById(Long bookId);

    List<BookDTO> findBooksByTitle(String title);

    List<BookDTO> findBooksByTitleAndAuthor(String title, String author);


    List<BookDTO> findBooksByCriteria(String title, String author,String isbn, String barcodeNumber);






}
