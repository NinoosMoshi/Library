package com.ninos.service.impl;

import com.ninos.dto.AddressDTO;
import com.ninos.entity.Address;
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
        Address address = AddressMapper.addressDtoToEntity(addressDTO);
        Address savedAddress = addressRepository.save(address);

        return AddressMapper.addressEntityToDto(savedAddress);
    }


    @Override
    public List<AddressDTO> getAllAddresses() {
        List<Address> addressList = addressRepository.findAll();
        return addressList.stream().map(AddressMapper::addressEntityToDto).collect(Collectors.toList());
    }


    @Override
    public AddressDTO getAddressById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "ID", id));
        return AddressMapper.addressEntityToDto(address);
    }


    @Override
    public AddressDTO updateAddress(AddressDTO addressDTO) {
        Address address = addressRepository.findById(addressDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Address", "ID", addressDTO.getId()));
        updateAddressEntityFromDTO(address,addressDTO);
        Address savedAddress = addressRepository.save(address);
        return AddressMapper.addressEntityToDto(savedAddress);
    }

    @Override
    public void deleteAddressById(Long addressId) {
        if(!addressRepository.existsById(addressId)){
            throw new ResourceNotFoundException("Address", "ID", addressId);
        }
        addressRepository.deleteById(addressId);
    }

    public void updateAddressEntityFromDTO(Address address, AddressDTO addressDTO) {
        if(addressDTO.getStreetName() != null) address.setStreetName(addressDTO.getStreetName());
        if(addressDTO.getStreetNumber() != null) address.setStreetNumber(addressDTO.getStreetNumber());
        if(addressDTO.getZipCode() != null) address.setZipCode(addressDTO.getZipCode());
        if(addressDTO.getPlaceName() != null) address.setPlaceName(addressDTO.getPlaceName());
        if(addressDTO.getCountry() != null) address.setCountry(addressDTO.getCountry());
        if(addressDTO.getAdditionalInfo() != null) address.setAdditionalInfo(addressDTO.getAdditionalInfo());
    }


}
