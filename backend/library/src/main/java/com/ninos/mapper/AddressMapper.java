package com.ninos.mapper;

import com.ninos.dto.AddressDTO;
import com.ninos.entity.Address;
import org.springframework.beans.BeanUtils;

public class AddressMapper {

    public static AddressDTO addressEntityToDto(Address address){
        AddressDTO addressDTO = new AddressDTO();
        BeanUtils.copyProperties(address,addressDTO);
        return addressDTO;
    }


    public static Address addressDtoToEntity(AddressDTO addressDTO){
        Address address = new Address();
        BeanUtils.copyProperties(addressDTO, address);
        return address;
    }
}
