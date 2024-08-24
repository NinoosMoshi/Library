package com.ninos.service.impl;

import com.ninos.dto.RegisterDTO;
import com.ninos.entity.CheckoutRegister;
import com.ninos.mapper.RegisterMapper;
import com.ninos.repository.CheckoutRegisterRepository;
import com.ninos.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RegisterServiceImpl implements RegisterService {

    @Value("${library.loanPeriodInDays}")
    private int loanPeriodInDays;

    @Value("${library.overdueFineRate}")
    private double overdueFineRate;

    private final CheckoutRegisterRepository registerRepository;
    private final RegisterMapper registerMapper;


    @Override
    public RegisterDTO createRegister(RegisterDTO registerDTO) {
        CheckoutRegister register = registerMapper.registerDtoToEntity(registerDTO);

        // calculate due date
        LocalDate dueDate = calculateDate(register.getCheckoutDate());
        register.setDueDate(dueDate);

        CheckoutRegister savedRegister = registerRepository.save(register);
        return registerMapper.registerEntityToDto(savedRegister);
    }

    @Override
    public List<RegisterDTO> getAllRegisters() {
        List<CheckoutRegister> registers = registerRepository.findAll();
        return registers.stream().map(registerMapper::registerEntityToDto).collect(Collectors.toList());
    }

    @Override
    public RegisterDTO findRegisterById(Long id) {
        CheckoutRegister register = registerRepository.findById(id).get();
        return registerMapper.registerEntityToDto(register);
    }

    @Override
    public RegisterDTO updateRegister(RegisterDTO registerDTO) {
        // find existing register by id
        CheckoutRegister registerToUpdate = registerRepository.findById(registerDTO.getId()).get();

        // do partial update
        updateRegisterFromDTO(registerToUpdate, registerDTO);

        // calculate overdue fine
        calculateOverdueFine(registerToUpdate);

        // save updated register to DB
        CheckoutRegister savedRegister = registerRepository.save(registerToUpdate);

        // return register DTO using mapper
        return registerMapper.registerEntityToDto(savedRegister);
    }

    private void calculateOverdueFine(CheckoutRegister register) {
        if (register.getReturnDate() != null && register.getReturnDate().isAfter(register.getDueDate())) {
            // calculate how many days between the date pass and return
            long daysOverdue = ChronoUnit.DAYS.between(register.getDueDate(), register.getReturnDate());

            double overdueFine = daysOverdue * overdueFineRate;
            register.setOverdueFine(overdueFine);
        }
    }

    private void updateRegisterFromDTO(CheckoutRegister registerToUpdate, RegisterDTO registerDTO) {
        if (registerDTO.getDueDate() != null) {
            registerToUpdate.setDueDate(LocalDate.parse(registerDTO.getDueDate()));
        }
        if (registerDTO.getReturnDate() != null) {
            registerToUpdate.setReturnDate(LocalDate.parse(registerDTO.getReturnDate()));
        }
    }


    private LocalDate calculateDate(LocalDate checkoutDate) {
        return checkoutDate.plusDays(loanPeriodInDays);
    }
}
