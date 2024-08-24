package com.ninos.controller;

import com.ninos.dto.MemberDTO;
import com.ninos.dto.RegisterDTO;
import com.ninos.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/registers")
public class RegisterController {

    private final RegisterService registerService;


    @PostMapping("/createRegister")
    public ResponseEntity<RegisterDTO> createRegister(@RequestBody RegisterDTO registerDTO) {
        RegisterDTO registerDTO1 = registerService.createRegister(registerDTO);
        return new ResponseEntity<>(registerDTO1, HttpStatus.CREATED);
    }

    @GetMapping("/listRegister")
    public ResponseEntity<List<RegisterDTO>> getAllRegisters() {
        List<RegisterDTO> registerDTOList = registerService.getAllRegisters();
        return ResponseEntity.ok().body(registerDTOList);
    }


    @GetMapping("/{id}")
    public ResponseEntity<RegisterDTO> getRegisterById(@PathVariable("id") Long registerId) {
        RegisterDTO registerDTO = registerService.findRegisterById(registerId);
        return new ResponseEntity<>(registerDTO, HttpStatus.OK);
    }


    @PatchMapping("/updateRegister/{id}")
    public ResponseEntity<RegisterDTO> updateRegister(@PathVariable("id") Long id, @RequestBody RegisterDTO registerDTO) {
        registerDTO.setId(id);
        RegisterDTO updatedRegister = registerService.updateRegister(registerDTO);
        return new ResponseEntity<>(updatedRegister, HttpStatus.OK);
    }


}
