package com.ninos.mapper;

import com.ninos.dto.RegisterDTO;
import com.ninos.entity.Book;
import com.ninos.entity.CheckoutRegister;
import com.ninos.entity.Member;
import com.ninos.repository.BookRepository;
import com.ninos.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Component
public class RegisterMapper {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    public RegisterDTO registerEntityToDto(CheckoutRegister register) {
        RegisterDTO registerDTO = new RegisterDTO();

        registerDTO.setMemberId(register.getMember().getId());
        registerDTO.setBookId(register.getBook().getId());

        // convert dates to String
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        registerDTO.setCheckoutDate(register.getCheckoutDate().format(formatter));
        registerDTO.setDueDate(register.getDueDate().format(formatter));
        if (register.getReturnDate() != null) {
            registerDTO.setReturnDate(register.getReturnDate().format(formatter));
        }
        BeanUtils.copyProperties(register, registerDTO);
        return registerDTO;
    }


    public CheckoutRegister registerDtoToEntity(RegisterDTO registerDTO) {
        CheckoutRegister register = new CheckoutRegister();

        // find member and book by id
        Member member = memberRepository.findById(registerDTO.getMemberId()).get();
        Book book = bookRepository.findById(registerDTO.getBookId()).get();
        register.setMember(member);
        register.setBook(book);

        // parse dates
        register.setCheckoutDate(LocalDate.parse(registerDTO.getCheckoutDate()));

        if(registerDTO.getDueDate() != null){
            register.setDueDate(LocalDate.parse(registerDTO.getDueDate()));
        }
        if (registerDTO.getReturnDate() != null) {
            register.setReturnDate(LocalDate.parse(registerDTO.getReturnDate()));
        }


        BeanUtils.copyProperties(registerDTO, register);
        return register;
    }

}
