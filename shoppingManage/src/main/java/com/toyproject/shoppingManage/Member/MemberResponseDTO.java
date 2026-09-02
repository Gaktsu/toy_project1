package com.toyproject.shoppingManage.Member;

public record MemberResponseDTO(Long id, String name, String email) {
    public static MemberResponseDTO from(Member member){
        return new MemberResponseDTO(
                member.getId(),
                member.getName(),
                member.getEmail()
        );
    }
}
