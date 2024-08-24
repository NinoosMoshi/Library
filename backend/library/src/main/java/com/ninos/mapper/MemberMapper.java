package com.ninos.mapper;

import com.ninos.dto.MemberDTO;
import com.ninos.entity.Member;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MemberMapper {

    public static MemberDTO memberEntityToDto(Member member){
        MemberDTO memberDTO = new MemberDTO();

        // store localDate as String
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        if(member.getDateOfBirth() != null){
            memberDTO.setDateOfBirth(member.getDateOfBirth().format(formatter));
        }

        // map postal address
        if(member.getAddress() != null){
            memberDTO.setAddress(AddressMapper.addressEntityToDto(member.getAddress()));
        }

        if(member.getMembershipStarted() != null){
            memberDTO.setMembershipStarted(member.getMembershipStarted().format(formatter));
        }

        if(member.getMembershipEnded() != null){
            memberDTO.setMembershipEnded(member.getMembershipEnded().format(formatter));
        }

        BeanUtils.copyProperties(member,memberDTO);
        return memberDTO;
    }


    public static Member memberDtoToEntity(MemberDTO memberDTO){
        Member member = new Member();

        // map String from dto to LocalDate in entity
        member.setDateOfBirth(LocalDate.parse(memberDTO.getDateOfBirth()));

        // address mapping
        if(memberDTO.getAddress() != null){
            member.setAddress(AddressMapper.addressDtoToEntity(memberDTO.getAddress()));
        }

        member.setMembershipStarted(LocalDate.parse(memberDTO.getMembershipStarted()));
        member.setMembershipEnded(LocalDate.parse(memberDTO.getMembershipEnded()));

        BeanUtils.copyProperties(memberDTO,member);
        return member;
    }

}
