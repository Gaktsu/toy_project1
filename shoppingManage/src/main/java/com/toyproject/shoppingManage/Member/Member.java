package com.toyproject.shoppingManage.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Entity
@Getter
public class Member {

    @GeneratedValue
    @Id
    private Long id;

    private String name;

    @Column(unique=true)
    private String email;

    protected Member(){}

    public Member(String name, String email){
        this.name = name;
        this.email = email;
    }
}
