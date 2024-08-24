package com.ninos.controller;

import com.ninos.dto.BookDTO;
import com.ninos.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;


    @PostMapping("/addBook")
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) {
        BookDTO savedBookDTO = bookService.addBook(bookDTO);
        return new ResponseEntity<>(savedBookDTO, HttpStatus.CREATED);
    }


    @GetMapping("/listBook")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok().body(books);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") Long bookId) {
        BookDTO bookDTO = bookService.getBookById(bookId);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }


    @PatchMapping("/updateBook/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable("id") Long id, @RequestBody BookDTO bookDTO) {
        bookDTO.setId(id);
        BookDTO updatedBook = bookService.updateBook(bookDTO);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable("id") Long id) {
        bookService.deleteBookById(id);
        return new ResponseEntity<>("Book successfully deleted", HttpStatus.OK);
    }

    @GetMapping("/search-title")
    public ResponseEntity<List<BookDTO>> searchBooksByTitle(@RequestParam String title) {
        List<BookDTO> books = bookService.findBooksByTitle(title);
        return new ResponseEntity<>(books,HttpStatus.OK);
    }

    @GetMapping("/search-title-author")
    public ResponseEntity<List<BookDTO>> searchBooksByTitleAndAuthor(@RequestParam String title, @RequestParam String author) {
        List<BookDTO> books = bookService.findBooksByTitleAndAuthor(title,author);
        return new ResponseEntity<>(books,HttpStatus.OK);
    }

    // localhost:8080/api/books/search?title=the wind&author=jo&isbn=12&barcodeNumber=3
    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchBooks(@RequestParam(required = false) String title,
                                                     @RequestParam(required = false) String author,
                                                     @RequestParam(required = false) String isbn,
                                                     @RequestParam(required = false) String barcodeNumber
                                                     ) {
        List<BookDTO> books = bookService.findBooksByCriteria(title,author,isbn,barcodeNumber);
        return new ResponseEntity<>(books,HttpStatus.OK);
    }


}




