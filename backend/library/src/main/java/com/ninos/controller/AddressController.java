package com.ninos.controller;

import com.ninos.dto.AddressDTO;
import com.ninos.dto.BookDTO;
import com.ninos.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;


    @PostMapping("/createAddress")
    public ResponseEntity<AddressDTO> createAddress(@RequestBody AddressDTO addressDTO){
        AddressDTO address = addressService.createAddress(addressDTO);
        return new ResponseEntity<>(address, HttpStatus.CREATED);
    }


    @GetMapping("listAddress")
    public ResponseEntity<List<AddressDTO>> getAllAddresses(){
        List<AddressDTO> allAddresses = addressService.getAllAddresses();
        return ResponseEntity.ok(allAddresses);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id){
        AddressDTO addressDTO = addressService.getAddressById(id);
        return ResponseEntity.ok(addressDTO);
    }

    @PatchMapping("/updateAddress/{id}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable("id") Long id, @RequestBody AddressDTO addressDTO) {
        addressDTO.setId(id);
        AddressDTO updatedAddress = addressService.updateAddress(addressDTO);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAddress/{id}")
    public ResponseEntity<String> deleteAddressById(@PathVariable("id") Long id) {
        addressService.deleteAddressById(id);
        return new ResponseEntity<>("Address successfully deleted", HttpStatus.OK);
    }


}
