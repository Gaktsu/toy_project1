package com.toyproject.shoppingManage.Order;

import com.toyproject.shoppingManage.Order.OrderItems.OrderItemRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequestDTO(
        @NotNull(message = "memberId는 필수입니다.")
        @Min(value = 1)
        Long memberId,

        @NotEmpty(message = "하나 이상의 상품 존재해야 합니다.")
        List<@Valid OrderItemRequestDTO> orderItems
) {
}
