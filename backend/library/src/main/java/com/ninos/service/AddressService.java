package com.ninos.service;

import com.ninos.dto.AddressDTO;

import java.util.List;

public interface AddressService {

    AddressDTO createAddress(AddressDTO addressDTO);

    List<AddressDTO> getAllAddresses();

    AddressDTO getAddressById(Long id);

    AddressDTO updateAddress(AddressDTO addressDTO);

    void deleteAddressById(Long addressId);

}
