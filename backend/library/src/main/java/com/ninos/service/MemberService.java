package com.ninos.service;

import com.ninos.dto.MemberDTO;

import java.util.List;

public interface MemberService {

    MemberDTO addMember(MemberDTO memberDTO);

    List<MemberDTO> getAllMembers();

    MemberDTO getMemberById(Long memberId);

    MemberDTO updateMember(MemberDTO memberDTO);

    void deleteMemberById(Long memberId);

    List<MemberDTO> findMembersByCriteria(Long id, String firstName, String lastName, String barcodeNumber);



}
