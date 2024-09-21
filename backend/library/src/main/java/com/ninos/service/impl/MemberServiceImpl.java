package com.ninos.service.impl;

import com.ninos.dto.AddressDTO;
import com.ninos.dto.MemberDTO;
import com.ninos.entity.Member;
import com.ninos.entity.PostalAddress;
import com.ninos.mapper.AddressMapper;
import com.ninos.mapper.MemberMapper;
import com.ninos.repository.AddressRepository;
import com.ninos.repository.MemberRepository;
import com.ninos.service.MemberService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;
    private final AddressServiceImpl addressService;
    private final EntityManager entityManager;


    @Transactional
    @Override
    public MemberDTO addMember(MemberDTO memberDTO) {
        PostalAddress postalAddress = new PostalAddress();

        AddressDTO addressDTO = memberDTO.getAddress();
        if (addressDTO != null){
            postalAddress = AddressMapper.addressDtoToEntity(addressDTO);
            postalAddress = addressRepository.save(postalAddress);
        }

        Member member = MemberMapper.memberDtoToEntity(memberDTO);

        if (postalAddress != null) member.setPostalAddress(postalAddress);

        Member savedMember = memberRepository.save(member);
        return MemberMapper.memberEntityToDto(savedMember);
    }


    @Override
    public List<MemberDTO> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream().map(MemberMapper::memberEntityToDto).collect(Collectors.toList());
    }

    @Override
    public MemberDTO getMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId).get();
        return MemberMapper.memberEntityToDto(member);
    }


    @Transactional
    @Override
    public MemberDTO updateMember(MemberDTO memberDTO) {
        Member member = memberRepository.findById(memberDTO.getId()).get();
        updateMemberEntityFromDTO(member, memberDTO);
        Member savedMember = memberRepository.save(member);
        return MemberMapper.memberEntityToDto(savedMember);
    }


    @Override
    public void deleteMemberById(Long memberId) {
        memberRepository.deleteById(memberId);
    }



    @Override
    public List<MemberDTO> findMembersByCriteria(Long id, String firstName, String lastName, String barcodeNumber) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> memberRoot = cq.from(Member.class);
        List<Predicate> predicates = new ArrayList<>();

        if(id != null){
            predicates.add(cb.equal(memberRoot.get("id"), id));
        }
        if(firstName != null){
            predicates.add(cb.like(cb.lower(memberRoot.get("firstName")), "%" + firstName.toLowerCase() + "%"));
        }
        if(lastName != null){
            predicates.add(cb.like(cb.lower(memberRoot.get("lastName")), "%" + lastName.toLowerCase() + "%"));
        }
        if(barcodeNumber != null){
            predicates.add(cb.like(cb.lower(memberRoot.get("barcodeNumber")), "%" + barcodeNumber.toLowerCase() + "%"));
        }
        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        List<Member> result = entityManager.createQuery(cq).getResultList();
        return result.stream().map(MemberMapper::memberEntityToDto).collect(Collectors.toList());
    }






    private void updateMemberEntityFromDTO(Member member, MemberDTO memberDTO) {
        if(memberDTO.getFirstName() != null) member.setFirstName(memberDTO.getFirstName());
        if(memberDTO.getLastName() != null) member.setLastName(memberDTO.getLastName());
        if(memberDTO.getDateOfBirth() != null) member.setDateOfBirth(LocalDate.parse(memberDTO.getDateOfBirth()));
        if(memberDTO.getEmail() != null) member.setEmail(memberDTO.getEmail());
        if(memberDTO.getPhone() != null) member.setPhone(memberDTO.getPhone());
        if(memberDTO.getBarcodeNumber() != null) member.setBarcodeNumber(memberDTO.getBarcodeNumber());
        if(memberDTO.getMembershipStarted() != null) member.setMembershipStarted(LocalDate.parse(memberDTO.getMembershipStarted()));

        // check if the membership is ended
        if(memberDTO.getMembershipEnded() != null){
            if(memberDTO.getMembershipEnded().isEmpty()){
                member.setMembershipEnded(null);
                member.setIsActive(true);
            }else{
                member.setMembershipEnded(LocalDate.parse(memberDTO.getMembershipEnded()));
                member.setIsActive(false);
            }
        }

        // update the address
        if(memberDTO.getAddress() != null){
            // if the member already has the address, update it.
            // otherwise create a new address entity
            PostalAddress postalAddressToUpdate;
            if(member.getPostalAddress() != null){
                postalAddressToUpdate = member.getPostalAddress();
            }else {
                postalAddressToUpdate = new PostalAddress();
            }
            // to update address entity, we will use existing address service
            addressService.updateAddressEntityFromDTO(postalAddressToUpdate, memberDTO.getAddress());
            addressRepository.save(postalAddressToUpdate);
            member.setPostalAddress(postalAddressToUpdate);
        }

    }


}
