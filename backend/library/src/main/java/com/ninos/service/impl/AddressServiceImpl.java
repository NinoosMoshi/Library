package com.ninos.service.impl;

import com.ninos.dto.AddressDTO;
import com.ninos.entity.PostalAddress;
import com.ninos.exception.ResourceNotFoundException;
import com.ninos.mapper.AddressMapper;
import com.ninos.repository.AddressRepository;
import com.ninos.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;


    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {
        PostalAddress postalAddress = AddressMapper.addressDtoToEntity(addressDTO);
        PostalAddress savedPostalAddress = addressRepository.save(postalAddress);

        return AddressMapper.addressEntityToDto(savedPostalAddress);
    }


    @Override
    public List<AddressDTO> getAllAddresses() {
        List<PostalAddress> postalAddressList = addressRepository.findAll();
        return postalAddressList.stream().map(AddressMapper::addressEntityToDto).collect(Collectors.toList());
    }


    @Override
    public AddressDTO getAddressById(Long id) {
        PostalAddress postalAddress = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "ID", id));
        return AddressMapper.addressEntityToDto(postalAddress);
    }


    @Override
    public AddressDTO updateAddress(AddressDTO addressDTO) {
        PostalAddress postalAddress = addressRepository.findById(addressDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Address", "ID", addressDTO.getId()));
        updateAddressEntityFromDTO(postalAddress,addressDTO);
        PostalAddress savedPostalAddress = addressRepository.save(postalAddress);
        return AddressMapper.addressEntityToDto(savedPostalAddress);
    }

    @Override
    public void deleteAddressById(Long addressId) {
        if(!addressRepository.existsById(addressId)){
            throw new ResourceNotFoundException("Address", "ID", addressId);
        }
        addressRepository.deleteById(addressId);
    }

    public void updateAddressEntityFromDTO(PostalAddress postalAddress, AddressDTO addressDTO) {
        if(addressDTO.getStreetName() != null) postalAddress.setStreetName(addressDTO.getStreetName());
        if(addressDTO.getStreetNumber() != null) postalAddress.setStreetNumber(addressDTO.getStreetNumber());
        if(addressDTO.getZipCode() != null) postalAddress.setZipCode(addressDTO.getZipCode());
        if(addressDTO.getPlaceName() != null) postalAddress.setPlaceName(addressDTO.getPlaceName());
        if(addressDTO.getCountry() != null) postalAddress.setCountry(addressDTO.getCountry());
        if(addressDTO.getAdditionalInfo() != null) postalAddress.setAdditionalInfo(addressDTO.getAdditionalInfo());
    }


}
