package com.toyproject.shoppingManage.Member;

import com.toyproject.shoppingManage.ErrorCode;
import com.toyproject.shoppingManage.Member.Exception.DuplicateMemberException;
import com.toyproject.shoppingManage.Member.Exception.MemberNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    // ----------------------- FIELD --------------------------//

    private final MemberRepository memberRepository;

    // ----------------------- CONSTRUCTOR --------------------------//

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    // ----------------------- RESTAPI : GET --------------------------//

    public MemberResponseDTO requestGetMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        return MemberResponseDTO.from(member);
    }

    // ----------------------- RESTAPI : POST --------------------------//

    public MemberResponseDTO requestRegisterMember(MemberRequestDTO request){
        if(memberRepository.existsByEmail(request.email()))
            throw new DuplicateMemberException(ErrorCode.DUPLICATE_EMAIL);

        Member member = memberRepository.save(Member.from(request));

        return MemberResponseDTO.from(member);
    }

    // ----------------------- METHOD --------------------------//

    /*
    public Member getMember(Long id){
        return memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }
    */
}
