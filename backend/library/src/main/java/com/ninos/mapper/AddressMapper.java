package com.ninos.mapper;

import com.ninos.dto.AddressDTO;
import com.ninos.entity.PostalAddress;
import org.springframework.beans.BeanUtils;

public class AddressMapper {

    public static AddressDTO addressEntityToDto(PostalAddress postalAddress){
        AddressDTO addressDTO = new AddressDTO();
        BeanUtils.copyProperties(postalAddress,addressDTO);
        return addressDTO;
    }


    public static PostalAddress addressDtoToEntity(AddressDTO addressDTO){
        PostalAddress postalAddress = new PostalAddress();
        BeanUtils.copyProperties(addressDTO, postalAddress);
        return postalAddress;
    }
}

