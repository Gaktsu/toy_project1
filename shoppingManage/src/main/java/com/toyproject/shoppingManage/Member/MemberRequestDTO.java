package com.toyproject.shoppingManage.Member;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberRequestDTO(
        @NotBlank(message = "이름은 필수입니다.")
        @JsonProperty("name")
        String name,

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(
                regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
                message = "이메일 형식에 맞지 않습니다."
        )
        @JsonProperty("email")
        String email) {}
