package com.ninos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "postal_address_id")
    private PostalAddress postalAddress;

    private String email;

    private String phone;

    @Column(nullable = false)
    private LocalDate membershipStarted;

    private LocalDate membershipEnded;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(nullable = false)
    private String barcodeNumber;

}
