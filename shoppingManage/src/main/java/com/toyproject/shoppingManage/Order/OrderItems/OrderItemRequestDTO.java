package com.toyproject.shoppingManage.Order.OrderItems;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequestDTO(
        @NotNull
        Long itemId,

        @NotNull
        @Min(value = 1)
        int quantity
) {
}
