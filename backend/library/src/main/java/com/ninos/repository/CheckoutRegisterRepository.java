package com.ninos.repository;

import com.ninos.dto.RegisterDTO;
import com.ninos.entity.CheckoutRegister;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckoutRegisterRepository extends JpaRepository<CheckoutRegister,Long> {

    List<CheckoutRegister> findByMemberId(Long memberId);

    List<CheckoutRegister> findByBookId(Long bookId);

}
