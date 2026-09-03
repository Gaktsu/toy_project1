package com.toyproject.shoppingManage.Item;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ItemResponseDTO(Long id, String name, Integer price, Integer stock) {
    public static ItemResponseDTO from(Item item){
        return new ItemResponseDTO(
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getStock()
        );
    }
}
