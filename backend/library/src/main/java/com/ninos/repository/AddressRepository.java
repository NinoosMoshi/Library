package com.ninos.repository;

import com.ninos.entity.PostalAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<PostalAddress,Long> {
}
