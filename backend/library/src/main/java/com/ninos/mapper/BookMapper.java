package com.ninos.mapper;

import com.ninos.dto.BookDTO;
import com.ninos.entity.Book;
import org.springframework.beans.BeanUtils;

public class BookMapper {


    public static BookDTO bookEntityToDto(Book book){
        BookDTO bookDTO = new BookDTO();
        BeanUtils.copyProperties(book,bookDTO);
        return bookDTO;
    }


    public static Book bookDtoToEntity(BookDTO bookDTO){
        Book book = new Book();
        BeanUtils.copyProperties(bookDTO,book);
        return book;
    }


}
