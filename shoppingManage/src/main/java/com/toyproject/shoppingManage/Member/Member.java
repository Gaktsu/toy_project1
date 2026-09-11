package com.toyproject.shoppingManage.Member;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Entity
@Getter
public class Member {

    // ----------------------- FIELD --------------------------//

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;

    @Column(unique=true)
    private String email;

    // ----------------------- CONSTRUCTOR --------------------------//

    protected Member(){}

    public Member(String name, String email){
        this.name = name;
        this.email = email;
    }

    // ----------------------- FACTORY METHOD --------------------------//

    public static Member from(MemberRequestDTO request){
        return new Member(request.name(), request.email());
    }
}
