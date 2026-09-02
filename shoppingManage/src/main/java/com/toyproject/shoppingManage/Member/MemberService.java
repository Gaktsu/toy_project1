package com.toyproject.shoppingManage.Member;

import com.toyproject.shoppingManage.ErrorCode;
import com.toyproject.shoppingManage.Member.Exception.DuplicateMemberException;
import com.toyproject.shoppingManage.Member.Exception.MemberNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
    public MemberResponseDTO registerMember(MemberRequestDTO request){
        if(memberRepository.existsByEmail(request.email()))
            throw new DuplicateMemberException(ErrorCode.DUPLICATE_EMAIL);

        Member member = memberRepository.save(new Member(request.name(), request.email()));

        return MemberResponseDTO.from(member);
    }

    public MemberResponseDTO getMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        return MemberResponseDTO.from(member);
    }
}
