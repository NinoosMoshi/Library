package com.ninos.service.impl;

import com.ninos.dto.BookDTO;
import com.ninos.entity.Book;
import com.ninos.exception.ResourceNotFoundException;
import com.ninos.mapper.BookMapper;
import com.ninos.repository.BookRepository;
import com.ninos.service.BookService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final EntityManager entityManager;


    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        Book book = BookMapper.bookDtoToEntity(bookDTO);
        Book savedBook = bookRepository.save(book);

        return BookMapper.bookEntityToDto(savedBook);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(book -> BookMapper.bookEntityToDto(book)).collect(Collectors.toList());
    }

    @Override
    public BookDTO getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow( () -> new ResourceNotFoundException("Book", "ID", bookId));
        return BookMapper.bookEntityToDto(book);
    }

    @Override
    public BookDTO updateBook(BookDTO bookDTO) {
        Book book = bookRepository.findById(bookDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Book", "ID", bookDTO.getId()));
        updateBookEntityFromDTO(book,bookDTO);
        Book savedBook = bookRepository.save(book);
        return BookMapper.bookEntityToDto(savedBook);
    }

    @Override
    public void deleteBookById(Long bookId) {
        if(!bookRepository.existsById(bookId)){
            throw new ResourceNotFoundException("Book", "ID", bookId);
        }
        bookRepository.deleteById(bookId);
    }

    @Override
    public List<BookDTO> findBooksByTitle(String title) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        return books.stream().map(BookMapper::bookEntityToDto).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> findBooksByTitleAndAuthor(String title, String author) {
        List<Book> books = bookRepository.findByTitleAndAuthor(title, author);
        return books.stream().map(BookMapper::bookEntityToDto).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> findBooksByCriteria(String title, String author, String isbn, String barcodeNumber) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> book = cq.from(Book.class);
        List<Predicate> predicates = new ArrayList<>();
        if(title != null && !title.isEmpty()){
            predicates.add(cb.like(cb.lower(book.get("title")), "%" + title.toLowerCase() + "%"));
        }
        if(author != null && !author.isEmpty()){
            predicates.add(cb.like(cb.lower(book.get("author")), "%" + author.toLowerCase() + "%"));
        }
        if(isbn != null && !isbn.isEmpty()){
            predicates.add(cb.like(cb.lower(book.get("isbn")), "%" + isbn.toLowerCase() + "%"));
        }
        if(barcodeNumber != null && !barcodeNumber.isEmpty()){
            predicates.add(cb.like(cb.lower(book.get("barcodeNumber")), "%" + barcodeNumber.toLowerCase() + "%"));
        }
        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        List<Book> result = entityManager.createQuery(cq).getResultList();
        return result.stream().map(BookMapper::bookEntityToDto).collect(Collectors.toList());
    }




    private void updateBookEntityFromDTO(Book book, BookDTO bookDTO) {
        if(bookDTO.getTitle() != null){
            book.setTitle(bookDTO.getTitle());
        }
            if(bookDTO.getAuthor() != null){
            book.setAuthor(bookDTO.getAuthor());
        }
            if(bookDTO.getIsbn() != null){
            book.setIsbn(bookDTO.getIsbn());
        }
            if(bookDTO.getPublisher() != null){
            book.setPublisher(bookDTO.getPublisher());
        }
            if(bookDTO.getYearOfPublication() != null){
            book.setYearOfPublication(bookDTO.getYearOfPublication());
        }
            if(bookDTO.getPlaceOfPublication() != null){
            book.setPlaceOfPublication(bookDTO.getPlaceOfPublication());
        }
            if(bookDTO.getNoOfAvailableCopies() != null){
            book.setNoOfAvailableCopies(bookDTO.getNoOfAvailableCopies());
        }
            if(bookDTO.getBarcodeNumber() != null){
            book.setBarcodeNumber(bookDTO.getBarcodeNumber());
        }

    }


}
