package com.ninos.service;

import com.ninos.dto.RegisterDTO;

import java.util.List;

public interface RegisterService {

    RegisterDTO createRegister(RegisterDTO registerDTO);

    List<RegisterDTO> getAllRegisters();

    RegisterDTO findRegisterById(Long id);

    RegisterDTO updateRegister(RegisterDTO registerDTO);

}
