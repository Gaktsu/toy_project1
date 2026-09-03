package com.toyproject.shoppingManage.Item;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ItemRequestDTO(
        @JsonProperty("name")
        @NotBlank(message = "상품은 필수입니다")
        String name,

        @JsonProperty("price")
        @NotNull
        @Min(value = 1, message = "1 이상의 값을 입력해주세요.")
        Integer price,

        @JsonProperty("stock")
        @NotNull
        @Min(value = 0, message = "0 이상의 값을 입력해주세요.")
        Integer stock) {}
