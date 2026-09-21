package com.toyproject.shoppingManage.Item;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemUpdateRequestDTO(
        @JsonProperty("price")
        @Min(value = 1, message = "1 이상의 값을 입력해주세요.")
        Integer price,

        @JsonProperty("stock")
        @Min(value = 0, message = "0 이상의 값을 입력해주세요.")
        Integer stock
) {
}
