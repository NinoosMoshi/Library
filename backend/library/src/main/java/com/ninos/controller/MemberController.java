package com.ninos.controller;

import com.ninos.dto.MemberDTO;
import com.ninos.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    private final MemberService memberService;


    @PostMapping("/addMember")
    public ResponseEntity<MemberDTO> addMember(@RequestBody MemberDTO memberDTO) {
        MemberDTO savedMember = memberService.addMember(memberDTO);
        return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
    }


    @GetMapping("/listMember")
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        List<MemberDTO> memberDTOList = memberService.getAllMembers();
        return ResponseEntity.ok().body(memberDTOList);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable("id") Long memberId) {
        MemberDTO memberDTO = memberService.getMemberById(memberId);
        return new ResponseEntity<>(memberDTO, HttpStatus.OK);
    }


    @PatchMapping("/updateMember/{id}")
    public ResponseEntity<MemberDTO> updateMember(@PathVariable("id") Long id, @RequestBody MemberDTO memberDTO) {
        memberDTO.setId(id);
        MemberDTO updatedMember = memberService.updateMember(memberDTO);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }


    @DeleteMapping("/deleteMember/{id}")
    public ResponseEntity<String> deleteMemberById(@PathVariable("id") Long id) {
        memberService.deleteMemberById(id);
        return new ResponseEntity<>("Member successfully deleted", HttpStatus.OK);
    }

    // localhost:8080/api/members/search?cardNumber=4&firstName=t
    @GetMapping("/search")
    public ResponseEntity<List<MemberDTO>> searchMembers(@RequestParam(required = false) Long cardNumber,
                                                         @RequestParam(required = false) String firstName,
                                                         @RequestParam(required = false) String lastName,
                                                         @RequestParam(required = false) String barcodeNumber
    ) {
        List<MemberDTO> memberDTOList = memberService.findMembersByCriteria(cardNumber, firstName, lastName, barcodeNumber);
        return new ResponseEntity<>(memberDTOList, HttpStatus.OK);
    }


}
