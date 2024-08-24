package com.ninos.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private AddressDTO address;
    private String email;
    private String phone;
    private String membershipStarted;
    private String membershipEnded;
    private Boolean isActive;
    private String barcodeNumber;

}
